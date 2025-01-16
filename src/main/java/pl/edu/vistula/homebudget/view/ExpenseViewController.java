package pl.edu.vistula.homebudget.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.model.Expense;
import pl.edu.vistula.homebudget.service.CategoryService;
import pl.edu.vistula.homebudget.service.ExpenseService;
import pl.edu.vistula.homebudget.support.ExpenseMapper;

import java.util.List;

@Controller
@RequestMapping("/view/expenses")
public class ExpenseViewController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public ExpenseViewController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String expenses(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("totalExpenses", expenseService.getTotalExpenses());
        return "index";
    }

    @GetMapping("/add")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new ExpenseRequest());
        model.addAttribute("categories", categoryService.findAll());
        return "add-expense";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute ExpenseRequest expenseRequest) {
        expenseService.create(expenseRequest);
        return "redirect:/view/expenses";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        expenseService.delete(id);
        return "redirect:/view/expenses";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        ExpenseResponse expenseResponse = expenseService.find(id);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("expense", expenseResponse);
        return "edit-expense";
    }
//    @PostMapping("/edit/{id}")
//    public String update(@ModelAttribute UpdateExpenseRequest updateExpenseRequest, @PathVariable Long id) {
//        expenseService.update(id, updateExpenseRequest);
//        return "redirect:/view/expenses";
//    }
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute UpdateExpenseRequest updateExpenseRequest, @PathVariable Long id) {
        if (updateExpenseRequest.getCategoryId() == null) {
            throw new IllegalArgumentException("ID kategorii nie może być puste!");
        }
        expenseService.update(id, updateExpenseRequest);  // Przesłanie obiektu z `id`, `description`, `amount`, `date`, `categoryId`
        return "redirect:/view/expenses";
    }

    @GetMapping("/import")
    public String showImportPage() {
        return "index";  // Widok z formularzem
    }
    @PostMapping("/import")
    public String importExpenses(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Plik CSV nie został przesłany!");
        }
        expenseService.importFromCsv(file);  // Metoda importująca plik
        return "redirect:/view/expenses";  // Przekierowanie po zakończeniu importu
    }
}