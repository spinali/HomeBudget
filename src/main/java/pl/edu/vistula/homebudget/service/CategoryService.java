package pl.edu.vistula.homebudget.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.edu.vistula.homebudget.api.request.CategoryRequest;
import pl.edu.vistula.homebudget.api.request.UpdateCategoryRequest;
import pl.edu.vistula.homebudget.api.response.CategoryResponse;
import pl.edu.vistula.homebudget.model.Category;
import pl.edu.vistula.homebudget.repository.CategoryRepository;
import pl.edu.vistula.homebudget.repository.ExpenseRepository;
import pl.edu.vistula.homebudget.support.CategoryMapper;
import pl.edu.vistula.homebudget.support.exception.CategoryDeletionException;
import pl.edu.vistula.homebudget.support.exception.CategoryExceptionSupplier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ExpenseRepository expenseRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.expenseRepository = expenseRepository;
    }
    public CategoryResponse create(CategoryRequest categoryRequest) {
        if (categoryRepository.findByName(categoryRequest.getName().toLowerCase()).isPresent()){
            throw CategoryExceptionSupplier.categoryAlreadyExist(categoryRequest.getName()).get();
        }
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryRequest));
        return categoryMapper.toCategoryResponse(category);
    }
    public CategoryResponse find(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryExceptionSupplier.categoryNotFound(id));//to change
        return categoryMapper.toCategoryResponse(category);
    }
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryExceptionSupplier.categoryNotFound(id));

        if (expenseRepository.existsByCategoryId(id)) {
            throw CategoryExceptionSupplier.categoryDeletion(id).get();
        }

        categoryRepository.deleteById(category.getId());
    }

    public CategoryResponse update(Long id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryExceptionSupplier.categoryNotFound(id));

        Optional<Category> existingCategory = categoryRepository.findByName(updateCategoryRequest.getName().toLowerCase());

        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(category.getId())) {
            throw CategoryExceptionSupplier.categoryAlreadyExist(updateCategoryRequest.getName()).get();
        }

        categoryRepository.save(categoryMapper.toCategory(category, updateCategoryRequest));
        return categoryMapper.toCategoryResponse(category);
    }
}
