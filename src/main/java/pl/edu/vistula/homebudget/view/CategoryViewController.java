package pl.edu.vistula.homebudget.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.vistula.homebudget.api.request.CategoryRequest;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateCategoryRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.CategoryResponse;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.service.CategoryService;
import pl.edu.vistula.homebudget.service.ExpenseService;

@Controller
@RequestMapping("/view/categories")
public class CategoryViewController {
    private final CategoryService categoryService;
    public CategoryViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "list_category";
    }
    @GetMapping("/add")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("category", new CategoryRequest());
        model.addAttribute("categories", categoryService.findAll());
        return "add-category";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute CategoryRequest categoryRequest) {
        categoryService.create(categoryRequest);
        return "redirect:/view/categories";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/view/categories";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.find(id);
        model.addAttribute("category", categoryResponse);
        return "edit-category";
    }
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute UpdateCategoryRequest updateCategoryRequest, @PathVariable Long id) {
        categoryService.update(id, updateCategoryRequest);
        return "redirect:/view/categories";
    }
}