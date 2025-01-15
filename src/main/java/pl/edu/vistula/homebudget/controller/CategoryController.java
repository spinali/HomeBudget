package pl.edu.vistula.homebudget.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.vistula.homebudget.api.request.CategoryRequest;
import pl.edu.vistula.homebudget.api.response.CategoryResponse;
import pl.edu.vistula.homebudget.service.CategoryService;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/add")
    @Operation(summary = "create new category")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
}
