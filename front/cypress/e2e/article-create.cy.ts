

describe('Création d\'article', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formcontrolname="usernameOrEmail"]').type('Jane');
    cy.get('input[formcontrolname="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
    cy.contains('Créer un article').click();
    cy.url().should('include', '/article/create');
  });



  
  it('affiche le formulaire', () => {
    cy.get('input[formcontrolname="topicId"]').should('be.visible');
    cy.get('input[formcontrolname="title"]').should('be.visible');
    cy.get('textarea[formcontrolname="content"]').should('be.visible');
  });




  it('crée un article avec le thème Java', () => {
    cy.get('input[formcontrolname="topicId"]').type('Java');
    cy.get('input[formcontrolname="title"]').type('Security in Spring Boot');
    cy.get('textarea[formcontrolname="content"]').type('How to secure your Spring Boot application with JWT and Spring Security.');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/article/');
  });

  

  it('retourne au feed via le bouton back', () => {
    cy.get('.back-btn').click();
    cy.url().should('include', '/feed');
  });

  



  it('crée un article avec le thème JavaScript', () => {
    cy.get('input[formcontrolname="topicId"]').type('JavaScript');
    cy.get('input[formcontrolname="title"]').type('Mon article JavaScript');
    cy.get('textarea[formcontrolname="content"]').type('Contenu de l\'article JavaScript');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/article/');
  });





  
it('affiche le champ thème comme un input', () => {
  cy.get('input[formcontrolname="topicId"]').should('be.visible');
  cy.get('input[formcontrolname="topicId"]').should('have.attr', 'placeholder', 'Sélectionner un thème');
});
});
