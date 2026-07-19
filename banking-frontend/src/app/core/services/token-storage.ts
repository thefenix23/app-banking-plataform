import { Service } from '@angular/core';

@Service()
export class TokenStorageService {

  private tokenKey = 'auth_token';

  save(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  get(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clear(): void {
    localStorage.removeItem(this.tokenKey);
  }
}
