import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenStorageService } from '../services/token-storage';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const tokenStorage = inject(TokenStorageService);
  const token = tokenStorage.get();

  console.log('Interceptor ejecutandose, token: ', token);

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log('Header agregado: ', req.headers.get('Authorization'));
  }

  return next(req);
};
