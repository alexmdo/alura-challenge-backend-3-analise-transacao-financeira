package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.controller.dto.TransactionImportedDto;
import br.com.alexmdo.txanalyser.service.TransactionImportedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
