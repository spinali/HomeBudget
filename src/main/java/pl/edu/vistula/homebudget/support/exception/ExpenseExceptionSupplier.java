package pl.edu.vistula.homebudget.support.exception;

import java.util.function.Supplier;

public class ExpenseExceptionSupplier {
    public static Supplier<ExpenseNotFoundException> expenseNotFound(Long id){
        return () -> new ExpenseNotFoundException(id);
    }
}
