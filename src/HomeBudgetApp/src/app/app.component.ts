import {Component, OnInit, ViewChild} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {CategoryService} from './category.service';
import {ExpenseService} from './expense.service';
import {Expense} from './expense';
import {catchError, tap, throwError} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';
import {Category} from './category';
import {FormsModule, NgForm} from '@angular/forms';
import {BudgetService} from './budget.service';
import {Budget} from './budget';

@Component({
  selector: 'app-root',
  imports: [NgForOf, FormsModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'HomeBudgetApp';
  public expenses: Expense[] | undefined;
  public categories: Category[] | undefined;
  public budget: Budget | undefined;
  public selectedCategory: Category | undefined;
  public selectedExpense: Expense | undefined;

  public selectedFile: File | null = null;
  public headers: string[] | undefined;
  public mappingFields: { descriptionHeader: string, amountHeader: string, dateHeader: string, categoryHeader: string } = {
    descriptionHeader: '',
    amountHeader: '',
    dateHeader: '',
    categoryHeader: ''
  };
  public importMessage: string | null = null;
  public importError: string | null = null;


  constructor(private expenseService: ExpenseService, private categoryService: CategoryService, private budgetService: BudgetService) {

  }

  ngOnInit(): void {
    this.getExpenses();
    this.getCategories();
    this.getBudget();
  }

  public getExpenses(): void {
    this.expenseService.getExpenses().pipe(
      tap((response: Expense[]) => this.expenses = response),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }
  public getCategories(): void {
    this.categoryService.getCategories().pipe(
      tap((response: Category[]) => this.categories = response),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }
  public addCategories(addCategoryForm:NgForm): void {

    const closeButton = document.getElementById('closeAddCategoryForm');
    if (closeButton) {
      closeButton.click();
    }
    this.categoryService.addCategory(addCategoryForm.value).pipe(

      tap((response: Category) => {
        console.log(response);
        this.getCategories();
        this.getExpenses();
        addCategoryForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }
  public updateCategory(editCategoryForm:NgForm): void {

    const closeButton = document.getElementById('closeEditCategoryForm');
    if (closeButton) {
      closeButton.click();
    }
    this.categoryService.updateCategory(editCategoryForm.value).pipe(

      tap((response: Category) => {
        console.log(response);
        this.getCategories();
        this.getExpenses();
        editCategoryForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public deleteCategory(categoryId: number): void {
    const closeButton = document.getElementById('closeDeleteCategoryForm');
    if (closeButton) {
      closeButton.click();
    }
    this.categoryService.removeCategory(categoryId).pipe(

      tap((response: void) => {
        console.log(response);
        this.getCategories();
        this.getExpenses();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public addExpense(addExpenseForm:NgForm): void {

    const closeButton = document.getElementById('closeAddExpenseForm');
    if (closeButton) {
      closeButton.click();
    }
    this.expenseService.addExpense(addExpenseForm.value).pipe(

      tap((response: Expense) => {
        console.log(response);
        this.getExpenses();
        addExpenseForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public updateExpense(editExpenseForm:NgForm): void {

    const closeButton = document.getElementById('closeEditExpenseForm');
    if (closeButton) {
      closeButton.click();
    }
    this.expenseService.updateExpense(editExpenseForm.value).pipe(

      tap((response: Expense) => {
        console.log(response);
        this.getCategories();
        this.getExpenses();
        editExpenseForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public deleteExpense(expenseId: number): void {
    const closeButton = document.getElementById('closeDeleteExpenseForm');
    if (closeButton) {
      closeButton.click();
    }
    this.expenseService.removeExpense(expenseId).pipe(

      tap((response: void) => {
        console.log(response);
        this.getCategories();
        this.getExpenses();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public getBudget(): void {
    this.budgetService.getBudget().pipe(
      tap((response: Budget) => this.budget = response),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public updateBudget(editBudgetForm:NgForm): void {

    const closeButton = document.getElementById('closeEditBudgetForm');
    if (closeButton) {
      closeButton.click();
    }
    this.budgetService.updateBudget(editBudgetForm.value).pipe(

      tap((response: Budget) => {
        console.log(response);
        this.getBudget();
        editBudgetForm.reset();
      }),
      catchError((error: HttpErrorResponse) => {
        alert(error.message);
        return throwError(() => error);
      })
    ).subscribe();
  }

  public onCategoryModalOpen(category: Category): void {
    this.selectedCategory = category;
  }
  public onExpenseModalOpen(expense: Expense): void {
    this.selectedExpense = expense;
  }


  // Obsługa wyboru pliku z inputa (type="file")
  public onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  // Wysyłka pliku CSV do API – backend zwraca nagłówki
  public uploadFile(): void {
    if (!this.selectedFile) return;
    this.expenseService.uploadCsvFile(this.selectedFile).pipe(
      tap(response => {
        this.headers = response.headers;
        this.importMessage = 'Plik CSV przesłany pomyślnie. Wybierz mapowanie nagłówków.';
        this.importError = null;
      }),
      catchError((error: HttpErrorResponse) => {
        this.importError = error.message;
        return throwError(() => error);
      })
    ).subscribe();
  }

  // Potwierdzenie importu – wysłanie mapowania nagłówków do API
  public confirmImport(): void {
    this.expenseService.confirmImport(this.mappingFields).pipe(
      tap(response => {
        this.importMessage = 'CSV import zakończony sukcesem.';
        this.importError = null;
        // Opcjonalnie: zresetuj mapowanie i nagłówki
        this.headers = undefined;
        this.mappingFields = { descriptionHeader: '', amountHeader: '', dateHeader: '', categoryHeader: '' };
      }),
      catchError((error: HttpErrorResponse) => {
        this.importError = error.message;
        return throwError(() => error);
      })
    ).subscribe();
  }
}
