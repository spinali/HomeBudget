package pl.edu.vistula.homebudget.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.vistula.homebudget.model.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    @NotBlank
    private String description;
    @Positive
    private BigDecimal amount;
    @NotNull
    private LocalDate date;
    @NotNull
    private Long categoryId;
    @JsonCreator
    public ExpenseRequest(Long id, String description, BigDecimal amount, LocalDate date, Long categoryId) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
    }
}
