package pl.edu.vistula.homebudget.support.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id %s not found", id));
    }
}
