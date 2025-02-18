import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Expense} from './expense';
import {Category} from './category';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  constructor(private http: HttpClient) { }

  private url ="http://localhost:8080/api/expenses";

  public getExpenses(): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.url}`);
  }
  public addExpense(expense:Expense):Observable<Expense>{
    return this.http.post<Expense>(`${this.url}/add`, expense);
  }
  public updateExpense(expense:Expense):Observable<Expense>{
    return this.http.put<Expense>(`${this.url}/${expense.id}`, expense);
  }
  public removeExpense(expenseId:number):Observable<void>{
    return this.http.delete<void>(`${this.url}/${expenseId}`);
  }
}
