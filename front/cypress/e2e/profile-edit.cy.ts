describe('Modification du profil', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formcontrolname="usernameOrEmail"]').type('Jane');
    cy.get('input[formcontrolname="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
    cy.get('button[routerlink="/profile"]').click();
    cy.url().should('include', '/profile');
    cy.contains('Modifier le profil').click();
    cy.url().should('include', '/profile/edit');
  });





  it('affiche le formulaire de modification', () => {
    cy.contains('Modifier le profil').should('be.visible');
    cy.get('input[formcontrolname="username"]').should('be.visible');
    cy.get('input[formcontrolname="email"]').should('be.visible');
  });





  it('affiche les valeurs actuelles du profil', () => {
    cy.get('input[formcontrolname="username"]').should('have.value', 'Jane');
    cy.get('input[formcontrolname="email"]').should('have.value', 'jane@email.com');
  });





  it('affiche les erreurs de validation avec des champs vides', () => {
    cy.get('input[formcontrolname="username"]').clear();
    cy.get('input[formcontrolname="email"]').clear();
    cy.get('button[type="submit"]').click({ force: true });
    cy.get('mat-error').should('be.visible');
  });







  it('annule et retourne au profil', () => {
    cy.contains('Annuler').click();
    cy.url().should('include', '/profile');
  });





  
  it('modifie le mot de passe', () => {
    cy.get('input[formcontrolname="password"]').type('Jane1234!');
    cy.get('input[formcontrolname="confirmPassword"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.contains('Profil mis à jour avec succès !').should('be.visible');
  });
});