package pl.edu.vistula.homebudget.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.dto.CategoryStatisticsDto;
import pl.edu.vistula.homebudget.service.BudgetService;
import pl.edu.vistula.homebudget.service.CategoryService;
import pl.edu.vistula.homebudget.service.ExpenseService;
import pl.edu.vistula.homebudget.support.BudgetMapper;
import pl.edu.vistula.homebudget.support.ExpenseMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view/expenses")
public class ExpenseViewController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;

    public ExpenseViewController(ExpenseService expenseService, CategoryService categoryService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.budgetService=budgetService;
    }

    @GetMapping
    public String expenses(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("budget", budgetService.find(1L));
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
        if(expenseRequest.getDate()==null) {
            expenseRequest.setDate(LocalDate.now());
        }
        expenseService.create(expenseRequest);
        return "redirect:/view/expenses";
    }
    @PostMapping("/delete-multiple")
    public String deleteMultiple(@RequestParam List<Long> ids) {
        expenseService.delete(ids);
        return "redirect:/view/expenses";
    }
    @GetMapping("/delete/{id}")
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
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute UpdateExpenseRequest updateExpenseRequest, @PathVariable Long id) {
        if (updateExpenseRequest.getCategoryId() == null) {
            throw new IllegalArgumentException("ID kategorii nie może być puste!");
        }
        expenseService.update(id, updateExpenseRequest);  // Przesłanie obiektu z `id`, `description`, `amount`, `date`, `categoryId`
        return "redirect:/view/expenses";
    }
    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        List<CategoryStatisticsDto> statistics = expenseService.getCategoryStatistics();
        model.addAttribute("statistics", statistics);
        return "statistics";
    }

//    @GetMapping("/import")
//    public String showImportPage() {
//        return "index";  // Widok z formularzem
//    }
//    @PostMapping("/import")
//    public String importExpenses(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("Plik CSV nie został przesłany!");
//        }
//        expenseService.importFromCsv(file);  // Metoda importująca plik
//        return "redirect:/view/expenses";  // Przekierowanie po zakończeniu importu
//    }
    @GetMapping("/import")
    public String showImportForm(Model model) {
        return "import-expenses";
    }
    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<String> headers = expenseService.getCsvHeaders(file);
            model.addAttribute("headers", headers); // Przekazujemy nagłówki do szablonu
            return "import-expenses"; // Powrót do strony importu z danymi
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload CSV file: " + e.getMessage());
            return "import-expenses"; // Powrót do strony importu z błędem
        }
    }

    // Endpoint do potwierdzania importu z mapowaniem nagłówków
    @PostMapping("/confirm-import")
    public String confirmImport(@RequestParam Map<String, String> headerMapping, Model model) {
        try {
            expenseService.importCsv(headerMapping);
            model.addAttribute("message", "Import successful!");
            return "import-expenses"; // Powrót do strony importu z komunikatem
        } catch (Exception e) {
            model.addAttribute("error", "Failed to import CSV: " + e.getMessage());
            return "import-expenses"; // Powrót do strony importu z błędem
        }
    }
}