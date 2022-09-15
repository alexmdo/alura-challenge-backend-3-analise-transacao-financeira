package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.dto.TransactionDto;
import br.com.alexmdo.txanalyser.service.TransactionService;
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

        try {
            List<TransactionDto> transactions = transactionService.readFromFileAndValidate(file.getInputStream());
            transactionService.saveAll(TransactionDto.toModel(transactions));
        } catch (IllegalArgumentException e) {
            //TODO handle correcty in view layer!
            throw e;
        }

        return "home";
    }

}
