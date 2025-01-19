package pl.edu.vistula.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.vistula.homebudget.model.Budget;
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
