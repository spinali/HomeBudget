package pl.edu.vistula.homebudget.api.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String categoryName;
    private Long categoryId;

    public ExpenseResponse(Long id, String description, BigDecimal amount, LocalDate date, Long categoryId, String categoryName) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
