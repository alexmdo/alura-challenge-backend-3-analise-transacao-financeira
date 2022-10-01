package br.com.alexmdo.txanalyser.service.factory;

import br.com.alexmdo.txanalyser.controller.dto.BankDto;
import br.com.alexmdo.txanalyser.controller.dto.TransactionDto;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.StringUtils;

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

public class CSVFileReader implements FileReader {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

    @Override
    public List<TransactionDto> read(InputStream inputStream) throws IOException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withSkipLines(0)
                .withCSVParser(csvParser);
        try (CSVReader csvReader = csvReaderBuilder.build()) {
            List<TransactionDto> transactionDtos = new ArrayList<>();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                transactionDtos.add(new TransactionDto(new BankDto(line[0], line[1], line[2]), new BankDto(line[3], line[4], line[5]), StringUtils.isNotBlank(line[6]) ? new BigDecimal(line[6]).setScale(2, RoundingMode.HALF_UP) : null, StringUtils.isNotBlank(line[7]) ? LocalDateTime.parse(line[7], formatter) : null));
            }

            return transactionDtos;
        }
    }

}
