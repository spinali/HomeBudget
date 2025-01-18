package pl.edu.vistula.homebudget.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.vistula.homebudget.repository.CategoryStatistics;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class CategoryStatisticsDto implements CategoryStatistics {
    private String categoryName;
    private BigDecimal total;
    public CategoryStatisticsDto(String categoryName, BigDecimal total) {
        this.categoryName = categoryName;
        this.total = total;
    }
}
