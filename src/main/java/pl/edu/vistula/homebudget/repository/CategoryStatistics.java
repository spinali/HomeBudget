package pl.edu.vistula.homebudget.repository;

import java.math.BigDecimal;

public interface CategoryStatistics {
    String getCategoryName();
    BigDecimal getTotal();
}
