
describe('Profil utilisateur', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formcontrolname="usernameOrEmail"]').type('Jane');
    cy.get('input[formcontrolname="password"]').type('Jane1234!');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/feed');
    cy.get('button[routerlink="/profile"]').click();
    cy.url().should('include', '/profile');
  });

  
  it('affiche les informations du profil', () => {
    cy.contains('Profil utilisateur').should('be.visible');
    cy.contains('Nom d\'utilisateur').should('be.visible');
    cy.contains('Adresse e-mail').should('be.visible');
    cy.contains('Membre depuis').should('be.visible');
  });



  
  it('affiche le nom d\'utilisateur', () => {
    cy.contains('Jane').should('be.visible');
  });

  



  it('affiche l\'email', () => {
    cy.contains('jane@email.com').should('be.visible');
  });

  


  it('affiche la section Souscriptions', () => {
    cy.contains('Souscriptions').should('be.visible');
  });




  
 
  it('va vers la modification du profil', () => {
    cy.contains('Modifier le profil').click();
    cy.url().should('include', '/profile/edit');
  });

  




  it('se déconnecte', () => {
    cy.contains('Se déconnecter').click();
    cy.url().should('include', '/');
    cy.contains('Se connecter').should('be.visible');
  });




  
  
  it('affiche "Aucun abonnement" si la liste est vide', () => {
    cy.get('body').then(($body) => {
      if ($body.find('.subscription-card').length === 0) {
        cy.contains('Vous n\'êtes abonné à aucun thème.').should('be.visible');
      }
    });
  });
  
});