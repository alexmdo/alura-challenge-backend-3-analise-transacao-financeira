package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.controller.dto.*;
import br.com.alexmdo.txanalyser.model.Transaction;
import br.com.alexmdo.txanalyser.model.TransactionImported;
import br.com.alexmdo.txanalyser.service.TransactionImportedService;
import br.com.alexmdo.txanalyser.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionImportedService transactionImportedService;
    private final TransactionService transactionService;

    public TransactionController(TransactionImportedService transactionImportedService, TransactionService transactionService) {
        this.transactionImportedService = transactionImportedService;
        this.transactionService = transactionService;
    }

    @PostMapping("/upload")
    public String onUpload(Model model, MultipartFile file) throws IOException {
        System.out.println("File name: " + file.getName());
        System.out.println("File length: " + file.getSize() / (1024 * 1024) + " Mb");

        try {
            List<TransactionDto> transactionsDto = transactionService.readFromFileAndValidate(file.getInputStream());
            List<? extends Transaction> transactions = transactionService.saveAll(TransactionDto.toModel(transactionsDto));
            transactionImportedService.save(new TransactionImported(null, getTransactionDate(transactionsDto), LocalDateTime.now(), (List<Transaction>) transactions, null));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "transactions/home";
        }

        return "redirect:/home";
    }

    @GetMapping("/detail/{id}")
    public String detailTransaction(@PathVariable Long id, Model model) {
        TransactionImported transactionImported = transactionImportedService.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found!"));
        model.addAttribute("transactionImported", transactionImported.toTransactionImportedDto());
        model.addAttribute("transactions", transactionImported.toTransactionDto());
        return "transactions/details";
    }

    @GetMapping("/suspect")
    public String goToSuspectTransactions() {
        return "transactions/suspect-transactions";
    }

    @PostMapping("analyse")
    public String analyseSuspectTransactions(Model model) {
        SuspectTransactionDto suspectTransactionDto1 = new SuspectTransactionDto(
                new BankDto("Banco do Brasil", "0001", "0001-1"),
                new BankDto("Banco Santander", "0001", "0001-1"),
                new BigDecimal("850000.00")
        );
        SuspectTransactionDto suspectTransactionDto2 = new SuspectTransactionDto(
                new BankDto("Banco Bradesco", "0001", "0001-1"),
                new BankDto("Banco Itaú", "0001", "0001-1"),
                new BigDecimal("644918.31")
        );
        SuspectTransactionDto suspectTransactionDto3 = new SuspectTransactionDto(
                new BankDto("Banco Santander", "0001", "0001-1"),
                new BankDto("Nubank", "0001", "0001-1"),
                new BigDecimal("500000.00")
        );

        SuspectAccountDto suspectAccountDto1 = new SuspectAccountDto(
                new BankDto("Banco do Brasil", "0001", "0001-1"),
                new BigDecimal("3138219.12"),
                MovimentTypeDto.ENTRADA
        );
        SuspectAccountDto suspectAccountDto2 = new SuspectAccountDto(
                new BankDto("Banco do Brasil", "0001", "0001-1"),
                new BigDecimal("1098614.19"),
                MovimentTypeDto.SAIDA
        );
        SuspectAccountDto suspectAccountDto3 = new SuspectAccountDto(
                new BankDto("Banco Bradesco", "0001", "0001-1"),
                new BigDecimal("1012490.00"),
                MovimentTypeDto.SAIDA
        );

        SuspectAgencyDto suspectAgency1 = new SuspectAgencyDto(
                new BankDto("Banco do Brasil", "0001", null),
                new BigDecimal("12419124125.87"),
                MovimentTypeDto.SAIDA
        );
        SuspectAgencyDto suspectAgency2 = new SuspectAgencyDto(
                new BankDto("Banco do Brasil", "0005", null),
                new BigDecimal("4198015987.33"),
                MovimentTypeDto.SAIDA
        );
        SuspectAgencyDto suspectAgency3 = new SuspectAgencyDto(
                new BankDto("Banco Bradesco", "0012", null),
                new BigDecimal("1912484319.11"),
                MovimentTypeDto.ENTRADA
        );

        model.addAttribute("suspectTransactions", Arrays.asList(suspectTransactionDto1, suspectTransactionDto2, suspectTransactionDto2));
        model.addAttribute("suspectAccounts", Arrays.asList(suspectAccountDto1, suspectAccountDto2, suspectAccountDto3));
        model.addAttribute("suspectAgencies", Arrays.asList(suspectAgency1, suspectAgency2, suspectAgency3));
        return "transactions/suspect-transactions";
    }

    private LocalDate getTransactionDate(List<TransactionDto> transactions) {
        return transactions.get(0).getTransactionDate().toLocalDate();
    }

}
