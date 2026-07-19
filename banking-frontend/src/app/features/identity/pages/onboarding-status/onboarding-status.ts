import { Component, inject, OnInit, signal } from '@angular/core';
import { IdentityService } from '../../services/identity';
import { TokenStorageService } from '../../../../core/services/token-storage';
import { ActivatedRoute, Router } from '@angular/router';
import { OnboardingStatusResult } from '../../models/user.model';

@Component({
  selector: 'app-onboarding-status',
  imports: [],
  templateUrl: './onboarding-status.html',
  styleUrl: './onboarding-status.css',
})
export class OnboardingStatus implements OnInit {

  private identityService = inject(IdentityService);
  private tokenStorage = inject(TokenStorageService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  onboardingStatus = signal<OnboardingStatusResult | null>(null);
  loading = signal(false);
  error = signal<string | null>(null);
  userId = signal<string | null>(null);

  ngOnInit() {
   this.route.params.subscribe((params) => {
     this.userId.set(params['userId']);
     if (this.userId()) {
       this.loadStatus();
     }
   }) ;
  }

  private loadStatus(): void {
    const id = this.userId();
    if (!id) {
      this.error.set('ID de usuario no encontrado');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    console.log('Iniciando petición GET a onboarding-status...');
    console.log('Token actual: ', this.tokenStorage.get());

    this.identityService.getOnboardingStatus(id).subscribe({
      next: (result) => {
        console.log('Respuesta exitosa: ', result);
        this.onboardingStatus.set(result);
      },
      error: (err) => {
        console.log('Error en petición: ', err);
        this.error.set(err.error?.error || 'Error al cargar el estado. Intenta de nuevo.');
      },
      complete: () => {
        this.loading.set(false);
      },
    });
  }

  reload(): void {
    this.loadStatus();
  }

  logout(): void {
    this.tokenStorage.clear();
    this.router.navigate(['/login']);
  }
}
