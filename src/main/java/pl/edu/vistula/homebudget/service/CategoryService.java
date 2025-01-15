package pl.edu.vistula.homebudget.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.vistula.homebudget.api.request.CategoryRequest;
import pl.edu.vistula.homebudget.api.response.CategoryResponse;
import pl.edu.vistula.homebudget.model.Category;
import pl.edu.vistula.homebudget.repository.CategoryRepository;
import pl.edu.vistula.homebudget.repository.ExpenseRepository;
import pl.edu.vistula.homebudget.support.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    public CategoryResponse create(CategoryRequest categoryRequest) {
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryRequest));
        return categoryMapper.toCategoryResponse(category);
    }
    public CategoryResponse find(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);//to change
        return categoryMapper.toCategoryResponse(category);
    }
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        categoryRepository.deleteById(category.getId());
    }
}
