package pl.edu.vistula.homebudget.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import pl.edu.vistula.homebudget.support.exception.ExpenseExceptionSupplier;
import pl.edu.vistula.homebudget.support.ExpenseMapper;
import pl.edu.vistula.homebudget.support.exception.CategoryExceptionSupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;
    private List<CSVRecord> records;
    private List<String> headers;

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
    public void delete(List<Long> ids) {
        List<Expense> expenses = expenseRepository.findAllById(ids);
        expenseRepository.deleteAll(expenses);
    }
    public BigDecimal getTotalExpenses() {
        return expenseRepository.findAll().stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public List<CategoryStatisticsDto> getCategoryStatistics() {
        return expenseRepository.getCategoryStatistics().stream()
                .map(stat -> new CategoryStatisticsDto(stat.getCategoryName(), stat.getTotal()))
                .collect(Collectors.toList());
    }
    public List<String> getCsvHeaders(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = CSVFormat.DEFAULT.builder()
                    .setDelimiter(";")
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build()
                    .parse(reader);

            this.headers = new ArrayList<>(csvParser.getHeaderNames());
            this.records = csvParser.getRecords();
            return headers;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
    }
    public void importCsv(String descriptionHeader, String amountHeader, String dateHeader, String categoryHeader) {
        if (records == null) {
            throw new RuntimeException("No CSV file uploaded");
        }

        DateTimeFormatter csvDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Expense> expenses = new ArrayList<>();

        for (CSVRecord record : records) {
            ExpenseRequest expenseRequest = new ExpenseRequest();
            boolean isValid = true;

            try {
                // Mapowanie pól
                expenseRequest.setDescription(record.get(descriptionHeader));
                String amountValue = cleanNumber(record.get(amountHeader));
                BigDecimal amount = new BigDecimal(amountValue);

                if (amount.compareTo(BigDecimal.ZERO) >= 0) {
                    isValid = false;
                } else {
                    expenseRequest.setAmount(amount.negate());
                }

                expenseRequest.setDate(LocalDate.parse(record.get(dateHeader), csvDateFormatter));

                String categoryName = record.get(categoryHeader);
                Category category = categoryRepository.findByName(categoryName)
                        .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
                expenseRequest.setCategoryId(category.getId());

            } catch (Exception e) {
                isValid = false;
            }

            if (isValid) {
                Expense expense = expenseMapper.toExpense(expenseRequest, categoryRepository.findById(expenseRequest.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found")));
                expenses.add(expense);
            }
        }

        expenseRepository.saveAll(expenses);
        this.records = null;
    }

    private String cleanNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "0";
        }
        String cleanedValue = value.replaceAll("[^\\d.,-]", "").replace(",", ".");
        if (!cleanedValue.matches("-?\\d+(\\.\\d+)?")) {
            throw new NumberFormatException("Invalid number format: " + value);
        }
        return cleanedValue;
    }


}

