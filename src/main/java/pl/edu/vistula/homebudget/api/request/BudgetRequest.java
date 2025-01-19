package pl.edu.vistula.homebudget.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
public class BudgetRequest {
    private Long id;
    private BigDecimal amount;

    @JsonCreator
    public BudgetRequest(Long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }
}