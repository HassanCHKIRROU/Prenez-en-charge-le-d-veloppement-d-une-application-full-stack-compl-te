describe('Fil d\'actualité', () => {
  beforeEach(() => {
    
    cy.visit('/login');
    cy.get('input[formControlName="usernameOrEmail"]').type('Jane');
    cy.get('input[formControlName="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();

    
    cy.url().should('include', '/feed');
  });





  it('should display feed with articles', () => {
   
    cy.contains('Articles').should('be.visible');
    cy.get('.article-card').should('exist');
  });




  it('should display article title, author and date', () => {
    
    cy.get('.article-card').first().within(() => {
      cy.get('h2').should('be.visible'); // Titre
      cy.get('.article-info').should('be.visible'); // Auteur + Date
    });
  });




  it('should be able to sort articles', () => {
    // Vérifier que le bouton de tri est présent et cliquable
    cy.contains('Trier par').should('be.visible').click();
    cy.get('.sort-container mat-icon').should('exist');
  });





  it('should navigate to article detail on click', () => {
    cy.get('.article-card').first().click();
    cy.url().should('include', '/article/');
  });




  it('should display "Créer un article" button', () => {
    cy.contains('Créer un article').should('be.visible');
  });



  
  it('should navigate to create article page', () => {
    cy.contains('Créer un article').click();
    cy.url().should('include', '/article/create');
  });
});