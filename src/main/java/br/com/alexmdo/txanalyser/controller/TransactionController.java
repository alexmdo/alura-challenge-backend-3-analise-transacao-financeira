package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.model.TransactionImported;
import br.com.alexmdo.txanalyser.service.TransactionImportedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionImportedService transactionImportedService;

    public TransactionController(TransactionImportedService transactionImportedService) {
        this.transactionImportedService = transactionImportedService;
    }

    @GetMapping("/detail/{id}")
    public String detailTransaction(@PathVariable Long id, Model model) {
        TransactionImported transactionImported = transactionImportedService.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found!"));
        model.addAttribute("transactionImported", transactionImported.toTransactionImportedDto());
        model.addAttribute("transactions", transactionImported.toTransactionDto());
        return "transactions/details";
    }

}
