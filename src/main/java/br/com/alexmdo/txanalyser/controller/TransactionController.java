package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.controller.dto.*;
import br.com.alexmdo.txanalyser.controller.form.SuspectTransactionForm;
import br.com.alexmdo.txanalyser.model.Transaction;
import br.com.alexmdo.txanalyser.model.TransactionImported;
import br.com.alexmdo.txanalyser.model.dto.SuspectAccountDto;
import br.com.alexmdo.txanalyser.service.TransactionImportedService;
import br.com.alexmdo.txanalyser.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            List<TransactionDto> transactionsDto = transactionService.readFromFileAndValidate(file);
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
    public String goToSuspectTransactions(SuspectTransactionForm suspectTransactionForm) {
        return "transactions/suspect-transactions";
    }

    @PostMapping("analyse")
    public String analyseSuspectTransactions(@Valid SuspectTransactionForm suspectTransactionForm, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("error", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return "transactions/suspect-transactions";
        }

        List<Transaction> transactionsGreaterThanEqual100000 = transactionService.findByAmountGreaterThanEqual(suspectTransactionForm.date(), new BigDecimal("100000.00"));
        List<SuspectTransactionDto> suspectTransactions = transactionsGreaterThanEqual100000.stream().map(t -> new SuspectTransactionDto(
                new BankDto(t.getSourceBank(), t.getSourceAgency(), t.getSourceAccount()),
                new BankDto(t.getDestinationBank(), t.getDestinationAgency(), t.getDestinationAccount()),
                t.getAmount()
        )).collect(Collectors.toList());

        List<SuspectAccountDto> incomeOrOutcomeSuspectAccountByAmountGreaterThanEqual1000000 = transactionService.findIncomeOrOutcomeSuspectAccountByAmountGreaterThanEqual(suspectTransactionForm.date(), new BigDecimal("1000000.00"));
        List<br.com.alexmdo.txanalyser.controller.dto.SuspectAccountDto> suspectAccountDtos = incomeOrOutcomeSuspectAccountByAmountGreaterThanEqual1000000.stream().map(o -> new br.com.alexmdo.txanalyser.controller.dto.SuspectAccountDto(
                new BankDto(o.bank(), o.agency(), o.account()),
                o.amount(),
                MovimentTypeDto.valueOf(o.movimentType())
        )).collect(Collectors.toList());

        List<br.com.alexmdo.txanalyser.model.dto.SuspectAgencyDto> incomeOrOutcomeSuspectAgencyByAmountGreaterThanEqual1000000000 = transactionService.findIncomeOrOutcomeSuspectAgencyByAmountGreaterThanEqual(suspectTransactionForm.date(), new BigDecimal("1000000000.00"));
        List<SuspectAgencyDto> suspectAgenciesDtos = incomeOrOutcomeSuspectAgencyByAmountGreaterThanEqual1000000000.stream().map(o -> new SuspectAgencyDto(new BankDto(o.bank(), o.agency(), null), o.amount(), MovimentTypeDto.valueOf(o.movimentType()))).collect(Collectors.toList());

        model.addAttribute("suspectTransactions", suspectTransactions);
        model.addAttribute("suspectAccounts", suspectAccountDtos);
        model.addAttribute("suspectAgencies", suspectAgenciesDtos);
        return "transactions/suspect-transactions";
    }

    private LocalDate getTransactionDate(List<TransactionDto> transactions) {
        return transactions.get(0).transactionDate().toLocalDate();
    }

}
