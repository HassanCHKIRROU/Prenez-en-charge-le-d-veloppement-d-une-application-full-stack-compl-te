describe('Inscription', () => {
  beforeEach(() => {
    cy.visit('/register');
  });

  it('should show validation errors with invalid data', () => {
    cy.get('input[formControlName="username"]').type('ab');
    cy.get('input[formControlName="email"]').type('invalid-email');
    cy.get('input[formControlName="password"]').type('123');
    cy.get('button[type="submit"]').click();

    cy.get('mat-error').should('be.visible');
  });







  it('should register successfully', () => {
    const timestamp = Date.now();
    const username = `user${timestamp}`;
    const email = `user${timestamp}@test.com`;

    cy.get('input[formControlName="username"]').type(username);
    cy.get('input[formControlName="email"]').type(email);
    cy.get('input[formControlName="password"]').type('Test1234!');
    cy.get('button[type="submit"]').click();

    cy.url().should('include', '/feed');
    cy.contains('Articles').should('be.visible');
  });









  it('should show error if email already exists', () => {
    cy.get('input[formControlName="username"]').type('NewUser');
    cy.get('input[formControlName="email"]').type('fatima@email.com');
    cy.get('input[formControlName="password"]').type('Test1234!');
    cy.get('button[type="submit"]').click();

    
    cy.url().should('not.include', '/feed');
  });
});