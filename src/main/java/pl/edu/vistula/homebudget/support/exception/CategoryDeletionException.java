package pl.edu.vistula.homebudget.support.exception;

public class CategoryDeletionException extends RuntimeException {
    public CategoryDeletionException(Long id) {
        super(String.format("Category with id %s has items", id));
    }
}
