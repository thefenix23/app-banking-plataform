import { Routes } from '@angular/router';
import { Login } from './features/identity/pages/login/login';
import { Register } from './features/identity/pages/register/register';
import { OnboardingStatus } from './features/identity/pages/onboarding-status/onboarding-status';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'register',
    component: Register
  },
  {
    path: 'onboarding-status/:userId',
    component: OnboardingStatus
  },
];
