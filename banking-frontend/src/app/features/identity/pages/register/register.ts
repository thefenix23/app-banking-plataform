import { Component, inject, OnInit, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup, ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { IdentityService } from '../../services/identity';
import { Router, RouterLink } from '@angular/router';
import { ValidationError } from '@angular/forms/signals';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register implements OnInit {
  private fb = inject(FormBuilder);
  private identityService = inject(IdentityService);
  private router = inject(Router);

  registerForm = signal<FormGroup>(null!);
  loading = signal(false);
  error = signal<string | null>(null);
  success = signal(false);

  ngOnInit(): void {
    this.registerForm.set(
      this.fb.group(
        {
          email: ['', [Validators.required, Validators.email]],
          password: ['', [Validators.required, this.passwordValidator.bind(this)]],
          confirmPassword: ['', this.passwordValidator.bind(this)],
        },
        { validotors: this.passwordMatchValidator.bind(this) },
      ),
    );
  }

  private passwordValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.value;
    if (!password) return null;

    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /\d/.test(password);
    const isLongEnough = password.length >= 8;

    if (!hasUpperCase || !hasLowerCase || !hasNumber || !isLongEnough) {
      return { invalidPassword: true };
    }

    return null;
  }

  private passwordMatchValidator(group: AbstractControl): ValidationErrors | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;

    if (password && confirmPassword && password !== confirmPassword) {
      return { passwordMismatch: true };
    }

    return null;
  }

  register(): void {
    const form = this.registerForm();
    if (form.invalid) {
      this.error.set('Por favor completa todos los campos correctamente.');
      return;
    }

    this.loading.set(true);
    this.error.set(null);
    this.success.set(false);

    const { email, password } = form.value;

    this.identityService.register({ email, password }).subscribe({
      next: () => {
        this.success.set(true);
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (err) => {
        this.error.set(err.error?.error || 'Error al registrarse. Intente de nuevo.');
        this.loading.set(false);
      },
    });
  }
}
