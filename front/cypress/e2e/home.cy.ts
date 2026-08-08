describe('Page d\'accueil', () => {
  beforeEach(() => {
    cy.visit('/');
  });





  it('should display the home page', () => {
    
    cy.contains('Se connecter').should('be.visible');
    cy.contains("S'inscrire").should('be.visible');
  });




  it('should navigate to login', () => {
    cy.contains('Se connecter').click();
    cy.url().should('include', '/login');
  });




  
  it('should navigate to register', () => {
    cy.contains("S'inscrire").click();
    cy.url().should('include', '/register');
  });
});