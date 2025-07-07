Cypress.Commands.add('login', (email = 'aaa@bbb.com', password = 'password') => {
  cy.visit('/login');
  // Write the email
  cy.get('input').first().type(email); 
  cy.contains('Continuar').click();
  cy.get('input[type="password"]').type(password);
  cy.contains('Iniciar sesión').click();
  cy.url().should('include', '/'); 
});