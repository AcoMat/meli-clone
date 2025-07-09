describe('Header search', () => {
  it('should search for MLA49315128 using the header', () => {
    cy.visit('/');

    // Escribe en el input de búsqueda del header
    cy.get('input[name="query"]').type('MLA49315128');

    // Envía el formulario (puede ser con Enter o click en el botón)
    cy.get('form.header-search').submit();

    // Verifica que la URL cambió a la búsqueda
    cy.url().should('include', '/search?query=MLA49315128');

    // Verifica que el producto aparece en los resultados
    cy.contains('Homem Nos Natura 100ml - Masculino - Edp 30% Off - Prt Biene').should('exist');
  });
});