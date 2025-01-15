package pl.edu.vistula.homebudget.support;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.model.Expense;

@Component
public class ExpenseMapper {
    public Expense toExpense(ExpenseRequest expenseRequest) {
        return new Expense(expenseRequest.getDescription(), expenseRequest.getAmount(), expenseRequest.getDate(), expenseRequest.getCategory());
    }
    public ExpenseResponse toExpenseResponse(Expense expense) {
        return new ExpenseResponse(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDate());
    }
    public Expense toExpense(Expense expense, UpdateExpenseRequest updateExpenseRequest) {
        return new Expense(updateExpenseRequest.getId(), updateExpenseRequest.getDescription(), updateExpenseRequest.getAmount(), updateExpenseRequest.getDate(), updateExpenseRequest.getCategory());
    }

}
