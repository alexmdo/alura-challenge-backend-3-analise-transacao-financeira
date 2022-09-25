package br.com.alexmdo.txanalyser.service;

import br.com.alexmdo.txanalyser.controller.dto.BankDto;
import br.com.alexmdo.txanalyser.controller.dto.TransactionDto;
import br.com.alexmdo.txanalyser.model.Transaction;
import br.com.alexmdo.txanalyser.repository.TransactionRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

    private final Validator validator;

    private final TransactionRepository transactionRepository;

    public TransactionService(Validator validator, TransactionRepository transactionRepository) {
        this.validator = validator;
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDto> readFromFileAndValidate(InputStream inputStream) throws IOException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withSkipLines(0)
                .withCSVParser(csvParser);
        try (CSVReader csvReader = csvReaderBuilder.build()) {
            List<TransactionDto> transactionDtos = new ArrayList<>();

            LocalDateTime baseDate = null;
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                TransactionDto transactionDto = new TransactionDto(new BankDto(line[0], line[1], line[2]), new BankDto(line[3], line[4], line[5]), StringUtils.isNotBlank(line[6]) ? new BigDecimal(line[6]).setScale(2, RoundingMode.HALF_UP) : null, StringUtils.isNotBlank(line[7]) ? LocalDateTime.parse(line[7], formatter) : null);

                if (hasNoError(transactionDto) && isSameDate(baseDate, transactionDto)) {
                    transactionDtos.add(transactionDto);

                    if (baseDate == null) {
                        baseDate = transactionDto.getTransactionDate();
                    }
                }
            }

            checkIfCsvIsEmpty(transactionDtos);
            checkIfCsvWasAlreadyUploaded(baseDate);

            return transactionDtos;
        }
    }

    private void checkIfCsvIsEmpty(List<TransactionDto> transactionDtos) {
        if (transactionDtos.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty");
        }
    }

    private void checkIfCsvWasAlreadyUploaded(LocalDateTime baseDate) {
        List<Transaction> transactionByTransactionDate = transactionRepository.findByTransactionDate(baseDate);
        if (!transactionByTransactionDate.isEmpty()) {
            throw new IllegalArgumentException("Upload already done by given date");
        }
    }

    public List<? extends Transaction> saveAll(Iterable<? extends Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    private boolean hasNoError(TransactionDto transactionDto) {
        return validator.validate(transactionDto).isEmpty();
    }

    private boolean isSameDate(LocalDateTime baseDate, TransactionDto transactionDto) {
        return baseDate == null || transactionDto.getTransactionDate().toLocalDate().isEqual(baseDate.toLocalDate());
    }
}
