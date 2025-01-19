package pl.edu.vistula.homebudget.support;

import org.springframework.stereotype.Component;
import pl.edu.vistula.homebudget.api.request.BudgetRequest;
import pl.edu.vistula.homebudget.api.response.BudgetResponse;
import pl.edu.vistula.homebudget.model.Budget;
@Component
public class BudgetMapper {
    public Budget toBudget(Budget budget, BudgetRequest budgetRequest) {
        return new Budget(budgetRequest.getId(),budgetRequest.getAmount());
    }
    public BudgetResponse toBudgetResponse(Budget budget) {
        return new BudgetResponse(budget.getId(), budget.getAmount());
    }
}
