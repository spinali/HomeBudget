package pl.edu.vistula.homebudget.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.vistula.homebudget.api.request.BudgetRequest;
import pl.edu.vistula.homebudget.api.response.BudgetResponse;
import pl.edu.vistula.homebudget.service.BudgetService;

@Controller
@RequestMapping("/view/budget")
public class BudgetViewController {
    private final BudgetService budgetService;
    public BudgetViewController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
    @GetMapping("/edit")
    public String showUpdateForm(Model model) {
        BudgetResponse budgetResponse = budgetService.find(1L);
        model.addAttribute("budget", budgetResponse);
        return "edit-budget";
    }
    @PostMapping("/edit")
    public String update(@ModelAttribute BudgetRequest budgetRequest) {
        budgetRequest.setId(1L);
        budgetService.update(1L, budgetRequest);
        return "redirect:/view/expenses";
    }
}
