describe('Application MDD', () => {
  it('should display home page', () => {
    cy.visit('/');
    cy.get('.logo, img[alt="MDD"], header img').should('be.visible');
  });





  it('should navigate to login', () => {
    cy.visit('/');
    cy.contains('Se connecter').click();
    cy.url().should('include', '/login');
  });




  
  it('should navigate to register', () => {
    cy.visit('/');
    cy.contains('S\'inscrire').click();
    cy.url().should('include', '/register');
  });
});