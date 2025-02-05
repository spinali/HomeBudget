package pl.edu.vistula.homebudget.view;

import org.aspectj.weaver.ast.Or;
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
import pl.edu.vistula.homebudget.support.exception.CategoryAlreadyExistsException;
import pl.edu.vistula.homebudget.support.exception.CategoryDeletionException;
import pl.edu.vistula.homebudget.support.exception.CategoryNotFoundException;

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
    public String create(@ModelAttribute CategoryRequest categoryRequest, Model model) {
        try {
            categoryService.create(categoryRequest);
            return "redirect:/view/categories";
        }catch (CategoryAlreadyExistsException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("category", categoryRequest);
            return "add-category";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            categoryService.delete(id);
            return "redirect:/view/categories";
        } catch (CategoryNotFoundException | CategoryDeletionException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        model.addAttribute("categories", categoryService.findAll());
        return "list_category";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        try {
            CategoryResponse categoryResponse = categoryService.find(id);
            model.addAttribute("category", categoryResponse);
            return "edit-category";
        }catch (CategoryNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categories", categoryService.findAll());
            return "list_category";
        }
    }
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute UpdateCategoryRequest updateCategoryRequest, @PathVariable Long id, Model model) {
        try {
            categoryService.update(id, updateCategoryRequest);
            return "redirect:/view/categories";
        }catch (CategoryNotFoundException | CategoryAlreadyExistsException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("category", updateCategoryRequest);
            return "edit-category";
        }
    }
}