package pl.edu.vistula.homebudget.support.exception;

import java.util.function.Supplier;

public class CategoryExceptionSupplier {
    public static Supplier<CategoryNotFoundException> categoryNotFound(Long id) {
        return () -> new CategoryNotFoundException(id);
    }
    public static Supplier<CategoryDeletionException> categoryDeletion(Long id) {
        return() -> new CategoryDeletionException(id);
    }
    public static Supplier<CategoryAlreadyExistsException> categoryAlreadyExist(String name) {
        return () -> new CategoryAlreadyExistsException(name);
    }
}
