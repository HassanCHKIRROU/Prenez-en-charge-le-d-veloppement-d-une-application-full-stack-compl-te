describe('Gestion des articles', () => {
  beforeEach(() => {
    
    cy.visit('/login');
    cy.get('input[formControlName="usernameOrEmail"]').type('Jane');
    cy.get('input[formControlName="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
  });






  
  it('should navigate to create article page', () => {
    
    cy.visit('/article/create');
    cy.url().should('include', '/article/create');

  
    cy.contains('h1', 'Créer un nouvel article').should('be.visible');
  });
});