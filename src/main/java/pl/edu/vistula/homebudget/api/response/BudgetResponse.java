package pl.edu.vistula.homebudget.api.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class BudgetResponse {
    private Long id;
    private BigDecimal amount;
    public BudgetResponse(Long id, BigDecimal amount) {
        this.amount = amount;
        this.id = id;
    }
}
