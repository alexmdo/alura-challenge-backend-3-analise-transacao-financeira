package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.dto.TransactionDto;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public String analyseSuspectTransactions() {
        return "transactions/suspect-transactions";
    }

    private LocalDate getTransactionDate(List<TransactionDto> transactions) {
        return transactions.get(0).getTransactionDate().toLocalDate();
    }

}
