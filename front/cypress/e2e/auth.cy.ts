describe('Authentification', () => {
  beforeEach(() => {
    cy.visit('/login');
  });





  it('should show validation errors with empty form', () => {
    cy.get('button[type="submit"]').click();
    cy.contains('Ce champ est requis').should('be.visible');
  });





  it('should show error with invalid credentials', () => {
    cy.get('input[formControlName="usernameOrEmail"]').type('wronguser');
    cy.get('input[formControlName="password"]').type('WrongPass123!');
    cy.get('button[type="submit"]').click();
    cy.contains('Erreur de connexion').should('be.visible');
  });






  it('should login successfully', () => {
    cy.get('input[formControlName="usernameOrEmail"]').type('Jane');
    cy.get('input[formControlName="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
    cy.contains('Articles').should('be.visible');
  });





  
  it('should logout successfully', () => {
    
    cy.get('input[formControlName="usernameOrEmail"]').type('Jane');
    cy.get('input[formControlName="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');

    
    cy.get('button[routerLink="/profile"]').click(); 
    cy.url().should('include', '/profile');

    
    cy.contains('Se déconnecter').click();
    cy.url().should('include', '/');
    cy.contains('Se connecter').should('be.visible');
  });
});