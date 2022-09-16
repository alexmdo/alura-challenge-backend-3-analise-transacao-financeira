package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.dto.TransactionDto;
import br.com.alexmdo.txanalyser.dto.TransactionImportedDto;
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

    private final TransactionService transactionService;
    private final TransactionImportedService transactionImportedService;

    public HomeController(TransactionService transactionService, TransactionImportedService transactionImportedService) {
        this.transactionService = transactionService;
        this.transactionImportedService = transactionImportedService;
    }


    @GetMapping
    public String toHome(Model model) {
        model.addAttribute("importsDone", TransactionImportedDto.toDto(transactionImportedService.findAllAndSortByTransactionDate()));
        return "home";
    }

    @PostMapping("/upload")
    public String onUpload(Model model, MultipartFile file) throws IOException {
        System.out.println("File name: " + file.getName());
        System.out.println("File length: " + file.getSize() / (1024 * 1024) + " Mb");

        try {
            List<TransactionDto> transactions = transactionService.readFromFileAndValidate(file.getInputStream());
            transactionService.saveAll(TransactionDto.toModel(transactions));
            transactionImportedService.save(new TransactionImported(null, getTransactionDate(transactions), LocalDateTime.now()));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }

        return "redirect:/home";
    }

    private LocalDate getTransactionDate(List<TransactionDto> transactions) {
        return transactions.get(0).getTransactionDate().toLocalDate();
    }

}
