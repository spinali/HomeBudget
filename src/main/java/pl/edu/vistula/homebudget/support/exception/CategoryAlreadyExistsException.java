package pl.edu.vistula.homebudget.support.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String name) {
        super(String.format("Category '%s' already exists", name));
    }
}
