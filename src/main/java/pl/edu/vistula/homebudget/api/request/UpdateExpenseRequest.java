package pl.edu.vistula.homebudget.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateExpenseRequest extends ExpenseRequest {
    private final Long id;
    //private final LocalDate date;
    @JsonCreator
    public UpdateExpenseRequest(String description, BigDecimal amount,Long id) {
        super();
        this.id = id;
        //this.date = date;
    }
}
