package pl.edu.vistula.homebudget.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateExpenseRequest extends ExpenseRequest {
    private final Long id;
    @JsonCreator
    public UpdateExpenseRequest(Long id, String description, BigDecimal amount, LocalDate date, Long categoryId) {
        super(description, amount, date, categoryId);
        this.id = id;
    }

}
