import { TestBed } from '@angular/core/testing';
import { AreaComponent } from './area.component';

describe('AreaComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        AreaComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AreaComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'fixed-assets-app'`, () => {
    const fixture = TestBed.createComponent(AreaComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('fixed-assets-app');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AreaComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('fixed-assets-app app is running!');
  });
});
