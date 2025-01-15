package pl.edu.vistula.homebudget.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.model.Expense;
import pl.edu.vistula.homebudget.repository.ExpenseRepository;
import pl.edu.vistula.homebudget.support.ExpenseExceptionSupplier;
import pl.edu.vistula.homebudget.support.ExpenseMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }
    public ExpenseResponse create(ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.save(expenseMapper.toExpense(expenseRequest));
        return expenseMapper.toExpenseResponse(expense);
    }

    public ExpenseResponse find(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(ExpenseExceptionSupplier.expenseNotFound(id));
        return expenseMapper.toExpenseResponse(expense);
    }
    public ExpenseResponse update(Long id, UpdateExpenseRequest updateExpenseRequest) {
        Expense expense = expenseRepository.findById(id).orElseThrow(ExpenseExceptionSupplier.expenseNotFound(id));
        expenseRepository.save(expenseMapper.toExpense(expense, updateExpenseRequest));
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

    public void importFromCsv(MultipartFile file) {
       }
    }
