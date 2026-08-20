describe('Consultation d\'article', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formcontrolname="usernameOrEmail"]').type('Jane');
    cy.get('input[formcontrolname="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');

  
    cy.contains('Créer un article').click();
    cy.get('input[formcontrolname="topicId"]').type('Java');
    cy.get('input[formcontrolname="title"]').type('Security in Spring Boot');
    cy.get('textarea[formcontrolname="content"]').type('How to secure your Spring Boot application with JWT and Spring Security.');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/article/');

    
    cy.visit('/feed');
    cy.wait(30000);
  });

  




  it('consulte l\'article', () => {
    cy.contains('Security in Spring Boot').click();
    cy.url().should('include', '/article/');
    cy.contains('Security in Spring Boot').should('be.visible');
  });





 
  it('affiche les métadonnées de l\'article', () => {
    cy.contains('Security in Spring Boot').click();
    cy.get('.article-meta').should('be.visible');
    cy.get('.meta-author').should('contain', 'Jane');
    cy.get('.meta-topic').should('contain', 'Java');
  });






  it('affiche le contenu de l\'article', () => {
    cy.contains('Security in Spring Boot').click();
    cy.get('.article-content').should('be.visible');
  });






  it('ajoute un commentaire', () => {
    cy.contains('Security in Spring Boot').click();
    cy.get('textarea[formcontrolname="content"]').type('Great article, very helpful!');
    cy.get('.send-icon-btn').click();
    cy.contains('Great article, very helpful!').should('be.visible');
  });

  





  it('vide le formulaire après ajout de commentaire', () => {
    cy.contains('Security in Spring Boot').click();
    cy.get('textarea[formcontrolname="content"]').type('Test comment');
    cy.get('.send-icon-btn').click();
    cy.get('textarea[formcontrolname="content"]').should('have.value', '');
  });








  it('retourne au feed via le bouton back', () => {
    cy.contains('Security in Spring Boot').click();
    cy.get('.back-btn').click();
    cy.url().should('include', '/feed');
  });




  

  it('affiche le message si aucun commentaire', () => {
    cy.contains('Security in Spring Boot').click();
    cy.get('.no-comments').should('be.visible');
  });
});
