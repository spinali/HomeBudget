package pl.edu.vistula.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToExpenses() {
        return "redirect:/view/expenses";
    }
}
