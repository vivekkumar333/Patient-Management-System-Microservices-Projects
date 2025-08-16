import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs';
import { UserRegistrationRequest } from './models/user-registration-request';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private AUTH_SERVICE_BASE_URL = 'http://localhost:4005';

  constructor(private http: HttpClient) { }

  login(credentials: {userName: string, password: string}):Observable<any>{
    return this.http.post(`${this.AUTH_SERVICE_BASE_URL}/api/auth/login`,credentials).pipe(
      catchError(this.handleError)
    );
  }

  registerUser(req: UserRegistrationRequest): Observable<any>{
    return this.http.post(`${this.AUTH_SERVICE_BASE_URL}/api/user`,req).pipe(
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse){
    if(error.status === 0) {                       
      return throwError(()=> "Network issue. Please try again later.");   // Network or CORS error
    }

    // if(error.error?.msg){
    //   return throwError(()=> error.error.msg);    // Validation or application-specific errors
    // }

    if(error.error?.message){
      return throwError(()=> error.error.message);    // Validation or application-specific errors
    }

    if(error.error?.errors){                        // Bean validation errors (e.g., @NotNull, @Email)
      const messages = error.error.errors.map((e: any)=> e.defaultMessage).join(", ");
      return throwError(()=> messages);
    }

    return throwError(()=> "An unexpected error occurred.");     // Generic fallback
  }

}
