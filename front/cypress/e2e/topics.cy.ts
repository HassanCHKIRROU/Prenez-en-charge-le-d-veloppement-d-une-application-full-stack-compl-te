describe('Gestion des thèmes', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formcontrolname="usernameOrEmail"]').type('Jane');
    cy.get('input[formcontrolname="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
    cy.contains('Thème').click();
    cy.url().should('include', '/topics');
  });





  it('affiche la page des thèmes', () => {
    cy.contains('Thèmes').should('be.visible');
  });

  




  it('affiche la liste des thèmes', () => {
    cy.get('.topic-card').should('have.length.greaterThan', 0);
  });

 




  it('affiche le titre et la description d\'un thème', () => {
    cy.get('.topic-card').first().within(() => {
      cy.get('h2').should('be.visible');
      cy.get('.topic-content').should('be.visible');
    });
  });







   it('s\'abonne à un thème', () => {
    cy.get('button:contains("S\'abonner")').first().click();
    cy.contains('Déjà abonné').should('be.visible');
  });
  





  it('affiche les thèmes déjà abonnés', () => {
    cy.get('.subscribed-badge').should('exist');
  });






  it('charge les thèmes sans erreur', () => {
    cy.get('.loading').should('not.exist');
    cy.get('.error').should('not.exist');
  });






  
  it('gère l\'erreur de chargement des thèmes', () => {
    cy.intercept('GET', '/api/topics', {
      statusCode: 500,
      body: { message: 'Erreur serveur' }
    }).as('getTopicsError');
    
    cy.visit('/topics');
    cy.wait('@getTopicsError');
    cy.contains('Erreur lors du chargement des thèmes').should('be.visible');
  });
});