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
    cy.visit('/favorites');
    // Si el producto ya está en favoritos, lo elimina primero
    cy.get('body').then(($body) => {
      if ($body.find('a[href="/product/MLA49315128"]').length > 0) {
        cy.contains('Eliminar').click();
        cy.get('a[href="/product/MLA49315128"]').should('not.exist');
      }
    });
    // Ahora va a la página del producto y lo agrega a favoritos
    cy.visit('http://localhost:5173/product/MLA49315128');
    cy.get('.like-button').should('be.visible').click();
    // El botón debe mostrar la estrella llena (src contiene 'star-fill')
    cy.get('.like-button img').should('have.attr', 'src').and('include', 'star-fill');
    // Verifica que el producto aparece en la lista de favoritos
    cy.visit('/favorites');
    cy.get('a[href="/product/MLA49315128"]').should('exist');
    // O verifica que el título del producto esté presente
    cy.contains('Homem Nos Natura 100ml - Masculino - Edp 30% Off - Prt Biene').should('be.visible');
    // Eliminar el producto de favoritos
    cy.contains('Eliminar').click();
    // Verifica que el producto ya no aparece en la lista
    cy.get('a[href="/product/MLA49315128"]').should('not.exist');
  });

  // Test para agregar y quitar favoritos asegurando el estado inicial
  it('debería asegurar que el producto puede ser agregado y removido de favoritos correctamente', () => {
    cy.loginAdmin();
    cy.visit('/favorites');
    // Si el producto está en favoritos, lo elimina
    cy.get('body').then(($body) => {
      if ($body.find('a[href="/product/MLA49315128"]').length > 0) {
        cy.contains('Eliminar').click();
      }
    });
    // Verifica que el producto NO está en favoritos
    cy.get('a[href="/product/MLA49315128"]').should('not.exist');
    // Va a la página del producto y lo agrega a favoritos
    cy.visit('/product/MLA49315128');
    cy.get('.like-button').should('be.visible').click();
    // Verifica que el botón muestra la estrella llena
    cy.get('.like-button img').should('have.attr', 'src').and('include', 'star-fill');
    // Vuelve a favoritos y verifica que el producto está
    cy.visit('/favorites');
    cy.get('a[href="/product/MLA49315128"]').should('exist');
  });

});