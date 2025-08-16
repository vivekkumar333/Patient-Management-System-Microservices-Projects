import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  errorMessage = '';
  constructor(private authService: AuthService){}

  userLogin(loginForm: NgForm):void{
    if(loginForm.invalid) return;

    this.authService.login(loginForm.value).subscribe({
      next: (res)=> {
        alert("Login successfull!");
      },
      error: (err)=> {
        this.errorMessage = err;
      }
    });
  }
}
