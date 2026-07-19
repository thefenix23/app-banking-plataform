import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IdentityService } from '../../services/identity';
import { Router, RouterLink } from '@angular/router';
import { TokenStorageService } from '../../../../core/services/token-storage';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {
  private fb = inject(FormBuilder);
  private identityService = inject(IdentityService);
  private tokenStorage = inject(TokenStorageService);
  private router = inject(Router);

  loginForm = signal<FormGroup>(null!);
  loading = signal(false);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.loginForm.set(
      this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
      }),
    );
  }

  login(): void {
    const form = this.loginForm();

    if (form.invalid) {
      this.error.set('Por favor completa todos los campos correctamente.');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    this.identityService.login(form.value).subscribe({
      next: (result) => {
        console.log('Login exitoso');
        console.log('Token a guardar');

        this.tokenStorage.save(result.token);
        console.log('Token guardado en localStorage', this.tokenStorage);

        this.router.navigate(['/onboarding-status', result.userId]);
      },
      error: (err) => {
        this.error.set(err.error?.error || 'Error al ingresar. Intenta de nuevo.');
        this.loading.set(false);
      },
      complete: () => {
        this.loading.set(false);
      },
    });
  }
}
