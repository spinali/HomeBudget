package pl.edu.vistula.homebudget.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.dto.CategoryStatisticsDto;
import pl.edu.vistula.homebudget.model.Category;
import pl.edu.vistula.homebudget.model.Expense;
import pl.edu.vistula.homebudget.repository.CategoryRepository;
import pl.edu.vistula.homebudget.repository.ExpenseRepository;
import pl.edu.vistula.homebudget.support.ExpenseExceptionSupplier;
import pl.edu.vistula.homebudget.support.ExpenseMapper;
import pl.edu.vistula.homebudget.support.exception.CategoryExceptionSupplier;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.categoryRepository = categoryRepository;
    }
    public ExpenseResponse create(ExpenseRequest expenseRequest) {
        Category category = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(CategoryExceptionSupplier.categoryNotFound(expenseRequest.getCategoryId()));
        Expense expense = expenseRepository.save(expenseMapper.toExpense(expenseRequest, category));
        return expenseMapper.toExpenseResponse(expense);
    }

    public ExpenseResponse find(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(ExpenseExceptionSupplier.expenseNotFound(id));
        return expenseMapper.toExpenseResponse(expense);
    }
    public ExpenseResponse update(Long id, UpdateExpenseRequest updateExpenseRequest) {
        Expense expense = expenseRepository.findById(id).orElseThrow(ExpenseExceptionSupplier.expenseNotFound(id));
        Category category = categoryRepository.findById(updateExpenseRequest.getCategoryId()).orElseThrow(CategoryExceptionSupplier.categoryNotFound(updateExpenseRequest.getCategoryId()));
        expenseRepository.save(expenseMapper.toExpense(expense, updateExpenseRequest, category));
        return expenseMapper.toExpenseResponse(expense);
    }

    public List<ExpenseResponse> findAll() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenseRepository.findAll().stream().map(expenseMapper::toExpenseResponse).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(ExpenseExceptionSupplier.expenseNotFound(id));
        expenseRepository.deleteById(expense.getId());
    }
    public BigDecimal getTotalExpenses() {
        return expenseRepository.findAll().stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public List<CategoryStatisticsDto> getCategoryStatistics() {
        return expenseRepository.getCategoryStatistics().stream()
                .map(stat -> new CategoryStatisticsDto(stat.getCategoryName(), stat.getTotal()))
                .collect(Collectors.toList());
    }
    public void importFromCsv(MultipartFile file) {
       }
    }
