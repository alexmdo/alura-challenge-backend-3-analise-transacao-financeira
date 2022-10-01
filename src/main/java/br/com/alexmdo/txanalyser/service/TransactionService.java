package br.com.alexmdo.txanalyser.service;

import br.com.alexmdo.txanalyser.controller.dto.TransactionDto;
import br.com.alexmdo.txanalyser.model.Transaction;
import br.com.alexmdo.txanalyser.model.dto.SuspectAccountDto;
import br.com.alexmdo.txanalyser.model.dto.SuspectAgencyDto;
import br.com.alexmdo.txanalyser.repository.TransactionRepository;
import br.com.alexmdo.txanalyser.service.factory.FileReader;
import br.com.alexmdo.txanalyser.service.factory.FileReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final Validator validator;

    private final TransactionRepository transactionRepository;

    public TransactionService(Validator validator, TransactionRepository transactionRepository) {
        this.validator = validator;
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDto> readFromFileAndValidate(final MultipartFile file) throws IOException {
        FileReader fileReader = new FileReaderFactory<TransactionDto>().getFileReader(file.getContentType());

        List<TransactionDto> transactions = fileReader.read(file.getInputStream());
        final List<TransactionDto> transactionDtos = new ArrayList<>();

        LocalDateTime baseDate = null;
        for (TransactionDto transactionDto : transactions) {
            if (hasNoError(transactionDto) && isSameDate(baseDate, transactionDto)) {
                transactionDtos.add(transactionDto);

                if (baseDate == null) {
                    baseDate = transactionDto.getTransactionDate();
                }
            }

            checkIfFileIsEmpty(transactionDtos);
            checkIfFileWasAlreadyUploaded(baseDate);
        }

        return transactionDtos;
    }

    private void checkIfFileIsEmpty(List<TransactionDto> transactionDtos) {
        if (transactionDtos.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty");
        }
    }

    private void checkIfFileWasAlreadyUploaded(LocalDateTime baseDate) {
        List<Transaction> transactionByTransactionDate = transactionRepository.findByTransactionDate(baseDate);
        if (!transactionByTransactionDate.isEmpty()) {
            throw new IllegalArgumentException("Upload already done by given date");
        }
    }

    public List<? extends Transaction> saveAll(Iterable<? extends Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    public List<Transaction> findByAmountGreaterThanEqual(LocalDate yearMonth, BigDecimal amount) {
        return transactionRepository.findByAmountGreaterThanEqual(yearMonth.getYear(), yearMonth.getMonthValue(), amount);
    }

    public List<SuspectAccountDto> findIncomeOrOutcomeSuspectAccountByAmountGreaterThanEqual(LocalDate yearMonth, BigDecimal amount) {
        List<SuspectAccountDto> incomeSuspectAccountByAmountGreaterThanEqual = transactionRepository.findIncomeSuspectAccountByAmountGreaterThanEqual(yearMonth.getYear(), yearMonth.getMonthValue(), amount);
        List<SuspectAccountDto> outcomeSuspectAccountByAmountGreaterThanEqual = transactionRepository.findOutcomeSuspectAccountByAmountGreaterThanEqual(yearMonth.getYear(), yearMonth.getMonthValue(), amount);

        ArrayList<SuspectAccountDto> suspectAccountDtos = new ArrayList<>(incomeSuspectAccountByAmountGreaterThanEqual);
        suspectAccountDtos.addAll(outcomeSuspectAccountByAmountGreaterThanEqual);

        return suspectAccountDtos;
    }

    public List<SuspectAgencyDto> findIncomeOrOutcomeSuspectAgencyByAmountGreaterThanEqual(LocalDate yearMonth, BigDecimal amount) {
        List<SuspectAgencyDto> incomeSuspectAgencyByAmountGreaterThanEqual = transactionRepository.findIncomeSuspectAgencyByAmountGreaterThanEqual(yearMonth.getYear(), yearMonth.getMonthValue(), amount);
        List<SuspectAgencyDto> outcomeSuspectAgencyByAmountGreaterThanEqual = transactionRepository.findOutcomeSuspectAgencyByAmountGreaterThanEqual(yearMonth.getYear(), yearMonth.getMonthValue(), amount);

        ArrayList<SuspectAgencyDto> suspectAgencyDtos = new ArrayList<>(incomeSuspectAgencyByAmountGreaterThanEqual);
        suspectAgencyDtos.addAll(outcomeSuspectAgencyByAmountGreaterThanEqual);

        return suspectAgencyDtos;
    }

    private boolean hasNoError(TransactionDto transactionDto) {
        return validator.validate(transactionDto).isEmpty();
    }

    private boolean isSameDate(LocalDateTime baseDate, TransactionDto transactionDto) {
        return baseDate == null || transactionDto.getTransactionDate().toLocalDate().isEqual(baseDate.toLocalDate());
    }


}
