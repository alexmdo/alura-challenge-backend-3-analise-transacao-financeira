package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.dto.TransactionDto;
import br.com.alexmdo.txanalyser.dto.TransactionImportedDto;
import br.com.alexmdo.txanalyser.model.Transaction;
import br.com.alexmdo.txanalyser.model.TransactionImported;
import br.com.alexmdo.txanalyser.service.TransactionImportedService;
import br.com.alexmdo.txanalyser.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final TransactionImportedService transactionImportedService;

    public HomeController(TransactionImportedService transactionImportedService) {
        this.transactionImportedService = transactionImportedService;
    }

    @GetMapping
    public String toHome(Model model) {
        model.addAttribute("importsDone", TransactionImportedDto.toDto(transactionImportedService.findAllAndSortByTransactionDate()));
        return "transactions/home";
    }

}
