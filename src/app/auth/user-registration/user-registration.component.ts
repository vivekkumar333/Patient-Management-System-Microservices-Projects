import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { UserRole } from '../../enums/user-role.enum';
import { UserRegistrationRequest } from '../models/user-registration-request';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.css'
})
export class UserRegistrationComponent {
  registrationForm : FormGroup;
  roles:UserRole[] = Object.values(UserRole);

  successMessage= '';
  errorMessage= '';
  
  constructor(private fb: FormBuilder, private authService: AuthService){

    this.registrationForm = this.fb.group({
      name: ["", Validators.required],
      email: ["",Validators.required, Validators.email],
      password: ["",Validators.required],
      address: [""],
      dateOfBirth: ["",Validators.required],
      role: [UserRole.PATIENT, Validators.required]
    });
  }

  doUserRegistration(): void{
    if(this.registrationForm.invalid) return;

    const userReq: UserRegistrationRequest = this.registrationForm.value;

    this.authService.registerUser(userReq).subscribe({
      next: (res: any) => {
        console.log("User Response: "+res);
        this.successMessage = res.msg;
        this.errorMessage='';
        this.registrationForm.reset();
      },
      error: (err: any)=>{
        this.errorMessage = err;
        this.successMessage='';
      }
    });
  }
}
