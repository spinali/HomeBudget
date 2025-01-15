package pl.edu.vistula.homebudget.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String description;
    @PositiveOrZero
    private BigDecimal amount;
    @NotNull
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Expense(String description, BigDecimal amount, LocalDate date, Category category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
}
