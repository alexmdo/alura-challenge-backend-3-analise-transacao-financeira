package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.TransactionService;
import br.com.alexmdo.txanalyser.model.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final TransactionService transactionService;

    public HomeController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping
    public String toHome() {
        return "home";
    }

    @PostMapping("/upload")
    public String onUpload(Model model, MultipartFile file) throws IOException {
        System.out.println("File name: " + file.getName());
        System.out.println("File length: " + file.getSize() / (1024 * 1024) + " Mb");

        List<Transaction> transactions = transactionService.readFromFile(file.getInputStream());

        return "home";
    }

}
