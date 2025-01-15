package pl.edu.vistula.homebudget.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.vistula.homebudget.api.request.ExpenseRequest;
import pl.edu.vistula.homebudget.api.request.UpdateExpenseRequest;
import pl.edu.vistula.homebudget.api.response.ExpenseResponse;
import pl.edu.vistula.homebudget.model.Expense;
import pl.edu.vistula.homebudget.service.ExpenseService;

import java.util.List;

@Controller
@RequestMapping("/api/expenses/")
public class ExpenseController {
    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping("/add/")
    @Operation(summary = "create new expense")
    public ResponseEntity<ExpenseResponse> create(@RequestBody ExpenseRequest expenseRequest) {
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
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @RequestBody UpdateExpenseRequest updateExpenseRequest) {
        ExpenseResponse expenseResponse = expenseService.update(id, updateExpenseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(expenseResponse);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete expense by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
