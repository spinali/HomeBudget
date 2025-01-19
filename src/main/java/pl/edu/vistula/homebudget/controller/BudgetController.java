package pl.edu.vistula.homebudget.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.vistula.homebudget.api.request.BudgetRequest;
import pl.edu.vistula.homebudget.api.response.BudgetResponse;
import pl.edu.vistula.homebudget.model.Budget;
import pl.edu.vistula.homebudget.service.BudgetService;

@Controller
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetService budgetService;
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
    @GetMapping
    @Operation(summary = "Find budget with id 1")
    public ResponseEntity<BudgetResponse> getBudgets() {
        BudgetResponse budgetResponse = budgetService.find(1L);
        return ResponseEntity.status(HttpStatus.OK).body(budgetResponse);
    }
    @PutMapping
    @Operation(summary = "Update budget with id 1")
    public ResponseEntity<BudgetResponse> updateBudget(@RequestBody BudgetRequest budgetRequest) {
        BudgetResponse budgetResponse = budgetService.update(1L, budgetRequest);
        return ResponseEntity.status(HttpStatus.OK).body(budgetResponse);
    }
}
