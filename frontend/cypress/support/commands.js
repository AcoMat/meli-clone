Cypress.Commands.add('loginAdmin', () => {
  cy.intercept('POST', '/auth/login').as('loginRequest');
  cy.visit('/login');
  cy.get('input[name="email"]').type("matiasacosta@email.com");
  cy.contains('Continuar').click();
  cy.get('input[name="password"]').type('admin123');
  cy.contains('Iniciar sesión').click();
  cy.wait('@loginRequest').its('response.statusCode').should('eq', 200);
});

Cypress.Commands.add('registerRandomUser', () => {
  cy.intercept('POST', '/auth/register').as('registerRequest');
  cy.visit('/register');
  cy.get('input[name="email"]').type(`testuser${Date.now()}@mail.com`);
  cy.get('input[type="checkbox"]').check();
  cy.contains('Continuar').click();
  cy.get('input[name="name"]').type('Juan');
  cy.get('input[name="lastName"]').type('Pérez');
  cy.contains('Continuar').click();
  // Paso 3: Contraseña
  cy.get('input[name="password"]').type("Password1!");
  cy.get('input[name="confirmPassword"]').type("Password1!");
  cy.contains('Continuar').click();
  cy.wait('@registerRequest').its('response.statusCode').should('eq', 200);
});
