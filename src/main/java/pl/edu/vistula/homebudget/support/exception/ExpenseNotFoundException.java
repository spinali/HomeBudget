package pl.edu.vistula.homebudget.support.exception;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Long id) {
        super(String.format("Expense with id %s not found", id));
    }
}
