package br.com.alexmdo.txanalyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String toHome() {
        return "home";
    }

    @PostMapping("/upload")
    public String onUpload(Model model, MultipartFile file) {
        System.out.println("File name: " + file.getName());
        System.out.println("File length: " + file.getSize() / (1024 * 1024) + " Mb");
        return "home";
    }

}
