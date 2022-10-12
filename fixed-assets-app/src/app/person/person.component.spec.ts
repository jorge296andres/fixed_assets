import { TestBed } from '@angular/core/testing';
import { PersonComponent } from './person.component';

describe('PersonComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        PersonComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(PersonComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'fixed-assets-app'`, () => {
    const fixture = TestBed.createComponent(PersonComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('fixed-assets-app');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(PersonComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('fixed-assets-app app is running!');
  });
});
