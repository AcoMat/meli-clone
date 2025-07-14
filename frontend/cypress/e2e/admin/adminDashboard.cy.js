describe('Admin Dashboard', () => {
  const uniqueEmail = `testuser${Date.now()}@mail.com`;

  beforeEach(() => {
    cy.visit('/');
  });

  it('should display admin dashboard when admin user logs in', () => {
    // Login as admin
    cy.loginAdmin();
    
    // Verify admin dashboard is visible
    cy.contains('Bienvenido, Admin!').should('be.visible');
    cy.contains('Desde acá podes administrar el sitio').should('be.visible');
    
    // Verify admin components are present
    cy.contains('Usuarios de la pagina').should('be.visible');
    cy.contains('Productos más comprados').should('be.visible');
    cy.contains('Top Productos Favoritos').should('be.visible');
    cy.contains('Top Compradores').should('be.visible');
  });

  it('should not display admin dashboard for regular users', () => {
    // Login as regular user
    cy.registerUser(uniqueEmail, 'Password123!');
    
    // Verify admin dashboard is NOT visible
    cy.contains('Bienvenido, Admin!').should('not.exist');
    cy.contains('Desde acá podes administrar el sitio').should('not.exist');
    cy.contains('Usuarios de la pagina').should('not.exist');
    cy.contains('Productos más comprados').should('not.exist');
    cy.contains('Top Productos Favoritos').should('not.exist');
    cy.contains('Top Compradores').should('not.exist');
    cy.get('[data-cy="home-carousel"]').should('exist');
  });

  it('shouldnt show the dashboard if logged in', () => {
    // Try to visit admin dashboard directly
    cy.visit('/admin/users/1');
    cy.url().should('eq', `${Cypress.config().baseUrl}/`);
  });
});