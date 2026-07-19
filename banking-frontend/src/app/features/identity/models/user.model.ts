export type OnboardingStatus = 'PENDING' | 'IN_REVIEW' | 'APPROVED' | 'REJECTED';

export interface RegisterCredentials {
  email: string;
  password: string;
}

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface LoginResult {
  token: string;
  userId: string;
  onboardingStatus: OnboardingStatus;
}

export interface OnboardingStatusResult {
  userId: string;
  email: string;
  onboardingStatus: OnboardingStatus;
}
