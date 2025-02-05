package pl.edu.vistula.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.vistula.homebudget.model.Expense;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e.category.name AS categoryName, SUM(e.amount) AS total FROM Expense e GROUP BY e.category.name")
    List<CategoryStatistics> getCategoryStatistics();
    @Query("SELECT COUNT(e) > 0 FROM Expense e WHERE e.category.id = :categoryId")
    boolean existsByCategoryId(@Param("categoryId") Long categoryId);
}
