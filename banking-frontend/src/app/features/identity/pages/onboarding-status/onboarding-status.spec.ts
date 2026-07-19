import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnboardingStatus } from './onboarding-status';

describe('OnboardingStatus', () => {
  let component: OnboardingStatus;
  let fixture: ComponentFixture<OnboardingStatus>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OnboardingStatus],
    }).compileComponents();

    fixture = TestBed.createComponent(OnboardingStatus);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
