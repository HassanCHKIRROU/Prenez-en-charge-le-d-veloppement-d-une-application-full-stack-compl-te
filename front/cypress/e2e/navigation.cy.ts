describe('Navigation', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formControlName="usernameOrEmail"]').type('Jane');
    cy.get('input[formControlName="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
  });





  it('should navigate between pages', () => {
    //Aller aux Thèmes
    cy.contains('Thèmes').click();
    cy.url().should('include', '/topics');

    // Revenir au Feed
    cy.contains('Articles').click();
    cy.url().should('include', '/feed');

    // Aller au Profil
    cy.get('button[routerLink="/profile"], a[routerLink="/profile"]').click();
    cy.url().should('include', '/profile');
  });
});