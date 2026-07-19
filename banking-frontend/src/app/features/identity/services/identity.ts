import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  LoginCredentials, LoginResult,
  OnboardingStatusResult,
  RegisterCredentials
} from '../models/user.model';
import { Observable } from 'rxjs';

@Service()
export class IdentityService {

  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/identity';

  register(credentials: RegisterCredentials): Observable<OnboardingStatusResult> {
    return this.http.post<OnboardingStatusResult>(
      `${this.baseUrl}/register`,
      credentials,
    );
  }

  login(credentials: LoginCredentials): Observable<LoginResult> {
    return this.http.post<LoginResult>(
      `${this.baseUrl}/login`,
      credentials,
    );
  }

  getOnboardingStatus(userId:string): Observable<OnboardingStatusResult> {
    return this.http.get<OnboardingStatusResult>(
      `${this.baseUrl}/onboarding-status/${userId}`,
    );
  }
}
