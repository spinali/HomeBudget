import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category} from './category';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  private url ="http://localhost:8080/api/categories";

  public getCategories():Observable<Category[]>{
    return this.http.get<Category[]>(`${this.url}`);
  }
  public addCategory(category:Category):Observable<Category>{
    return this.http.post<Category>(`${this.url}/add`, category);
  }
  public updateCategory(category:Category):Observable<Category>{
    return this.http.put<Category>(`${this.url}/${category.id}`, category);
  }
  public removeCategory(categoryId:number):Observable<void>{
    return this.http.delete<void>(`${this.url}/${categoryId}`,);
  }
}
