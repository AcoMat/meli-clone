//TODO
describe('Pruebas de funcionalidad de favoritos', () => {
  // Test para agregar a favoritos sin login
  it('debería redireccionar a /login al intentar agregar a favoritos sin login', () => {
    cy.visit('http://localhost:5173/product/MLA49315128');
    // El botón de favoritos debe estar visible
    cy.get('.like-button').should('be.visible');
    cy.get('.like-button').click();
    cy.url().should('include', '/login');
  });

  // Test para agregar a favoritos con login
  it('debería agregar a favoritos el producto estando logueado', () => {
    cy.loginAdmin();
    cy.visit('http://localhost:5173/product/MLA49315128');
    // El botón de favoritos debe estar visible
    cy.get('.like-button').should('be.visible');
    cy.get('.like-button').click();
    // El botón debe mostrar la estrella llena (src contiene 'star-fill')
    cy.get('.like-button img').should('have.attr', 'src').and('include', 'star-fill');
    // Verifica que el producto aparece en la lista de favoritos
    cy.visit('/favorites');
    // Busca el enlace del producto en la página de favoritos
    cy.get('a[href="/product/MLA49315128"]').should('exist');
    // O verifica que el título del producto esté presente
    cy.contains('Homem Nos Natura 100ml - Masculino - Edp 30% Off - Prt Biene').should('be.visible');
    // Eliminar el producto de favoritos
    cy.contains('Eliminar').click();
    // Verifica que el producto ya no aparece en la lista
    cy.get('a[href="/product/MLA49315128"]').should('not.exist');
  });
});