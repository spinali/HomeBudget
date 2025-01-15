package pl.edu.vistula.homebudget.support;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.edu.vistula.homebudget.api.request.CategoryRequest;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.response.CategoryResponse;
import pl.edu.vistula.homebudget.model.Category;

@Component
public class CategoryMapper {
    public Category toCategory(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.getName());
    }
    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
