package pl.edu.vistula.homebudget.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.vistula.homebudget.api.request.CategoryRequest;
import pl.edu.vistula.homebudget.api.request.UpdateCategoryRequest;
import pl.edu.vistula.homebudget.api.response.CategoryResponse;
import pl.edu.vistula.homebudget.client.AIClient;
import pl.edu.vistula.homebudget.service.CategoryService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final AIClient aiClient;


    public CategoryController(CategoryService categoryService, AIClient geminiApiClient, AIClient aiClient) {
        this.categoryService = categoryService;
        this.aiClient = aiClient;
    }

    @PostMapping("/add")
    @Operation(summary = "create new category")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find category by id")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.find(id);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "find all categories")
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categoryResponses = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete category by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update category by id")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        CategoryResponse categoryResponse = categoryService.update(id, updateCategoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }

    @GetMapping("/generate")
    @Operation(summary = "generate category")
    public ResponseEntity<List<CategoryResponse>> generateCategories() {
        List<CategoryResponse> categoryResponse = categoryService.saveGeneratedCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }
}
