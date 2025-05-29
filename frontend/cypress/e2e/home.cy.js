describe('template spec', () => {
  it('passes', () => {
    cy.visit('/')
    cy.get('#1').should('be.visible')
  })
})