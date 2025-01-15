package pl.edu.vistula.homebudget.support.exception;

import java.util.function.Supplier;

public class CategoryExceptionSupplier {
    public static Supplier<CategoryNotFoundException> categoryNotFound(Long id) {
        return () -> new CategoryNotFoundException(id);
    }
}
