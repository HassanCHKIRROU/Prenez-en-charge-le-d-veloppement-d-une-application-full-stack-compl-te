// Commande personnalisée pour se connecter
Cypress.Commands.add('login', (username: string, password: string) => {
  cy.visit('/login');
  cy.get('input[formcontrolname="usernameOrEmail"]').type(username);
  cy.get('input[formcontrolname="password"]').type(password);
  cy.get('button[type="submit"]').click();
  cy.url().should('include', '/feed');
});

// Commande personnalisée pour créer un article
Cypress.Commands.add('createArticle', (title: string, content: string, topic: string) => {
  cy.contains('Créer un article').click();
  cy.get('input[formcontrolname="title"]').type(title);
  cy.get('textarea[formcontrolname="content"]').type(content);
  cy.get('mat-select[formcontrolname="topicId"]').click();
  cy.contains(topic).click();
  cy.get('button[type="submit"]').click();
  cy.url().should('include', '/article/');
});

// Déclaration TypeScript
declare global {
  namespace Cypress {
    interface Chainable {
      login(username: string, password: string): Chainable<void>;
      createArticle(title: string, content: string, topic: string): Chainable<void>;
    }
  }
}