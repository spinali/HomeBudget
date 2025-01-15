package pl.edu.vistula.homebudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.vistula.homebudget.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
