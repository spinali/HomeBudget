package pl.edu.vistula.homebudget.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.service.ExpenseService;
import pl.edu.vistula.homebudget.dto.CategoryStatisticsDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping("/add")
    @Operation(summary = "create new expense")
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse expenseResponse = expenseService.create(expenseRequest);
        return new ResponseEntity<>(expenseResponse, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    @Operation(summary = "find expense by id")
    public ResponseEntity<ExpenseResponse> find(@PathVariable Long id) {
        ExpenseResponse expenseResponse = expenseService.find(id);
        return new ResponseEntity<>(expenseResponse, HttpStatus.OK);
    }
    @GetMapping
    @Operation(summary = "Find all expenses")
    public ResponseEntity<List<ExpenseResponse>> findAll() {
        List<ExpenseResponse> expenseResponses = expenseService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(expenseResponses);
    }
    @PutMapping("/{id}")
    @Operation(summary = "update expense")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateExpenseRequest updateExpenseRequest) {
        ExpenseResponse expenseResponse = expenseService.update(id, updateExpenseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(expenseResponse);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete expense by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/statistics")
    @ResponseBody
    @Operation(summary = "get statistics")
    public List<CategoryStatisticsDto> getStatistics() {
        return expenseService.getCategoryStatistics();
    }
    @GetMapping("/upload")
    public String showUploadPage(Model model) {
        return "import-expenses";
    }
    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<String> headers = expenseService.getCsvHeaders(file);
            model.addAttribute("headers", headers);
            return "import-expenses";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload file: " + e.getMessage());
            return "import-expenses";
        }
    }

    @PostMapping("/confirm-import")
    public String importCsv(
            @RequestParam String descriptionHeader,
            @RequestParam String amountHeader,
            @RequestParam String dateHeader,
            @RequestParam String categoryHeader,
            Model model) {

        try {
            expenseService.importCsv(descriptionHeader, amountHeader, dateHeader, categoryHeader);
            model.addAttribute("message", "CSV file imported successfully");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to import CSV: " + e.getMessage());
        }

        return "index";
    }
}
