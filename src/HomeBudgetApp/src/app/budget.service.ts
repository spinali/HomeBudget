import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Budget} from './budget';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  constructor(private http: HttpClient) { }
  private url ="http://localhost:8080/api/budget";

  public getBudget():Observable<Budget>{
    return this.http.get<Budget>(`${this.url}`);
  }
  public updateBudget(budget: Budget):Observable<Budget>{
    console.log(budget);
    return this.http.put<Budget>(`${this.url}`, {amount: budget.amount});
  }
}
