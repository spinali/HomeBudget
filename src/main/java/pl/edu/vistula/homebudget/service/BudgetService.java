package pl.edu.vistula.homebudget.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pl.edu.vistula.homebudget.api.request.BudgetRequest;
import pl.edu.vistula.homebudget.api.response.BudgetResponse;
import pl.edu.vistula.homebudget.model.Budget;
import pl.edu.vistula.homebudget.repository.BudgetRepository;
import pl.edu.vistula.homebudget.support.BudgetMapper;
import pl.edu.vistula.homebudget.support.CategoryMapper;

import java.math.BigDecimal;


@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = new BudgetMapper();
    }

    @PostConstruct
    public void initializeDefaultBudget(){
        if(!budgetRepository.existsById(1L)){
            Budget defaultBudget = Budget.builder().id(1L).amount(BigDecimal.ZERO).build();
            budgetRepository.save(defaultBudget);
        }
    }
    public BudgetResponse update(Long id, BudgetRequest budgetRequest) {
        Budget budget = budgetRepository.findById(id).orElseThrow(RuntimeException::new);
        budgetRepository.save(budgetMapper.toBudget(budget, budgetRequest));
        System.out.println("Aktualizowany budżet: " + budgetRequest);
        return budgetMapper.toBudgetResponse(budget);
    }
    public BudgetResponse find(Long id) {
        Budget budget = budgetRepository.findById(id).orElseThrow(RuntimeException::new);
        return budgetMapper.toBudgetResponse(budget);
    }
}
