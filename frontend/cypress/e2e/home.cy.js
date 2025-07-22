describe('Home Page e2e tests', () => {
  it('home page loads', () => {
    cy.visit('/')
    cy.get('#adscarousel').should('be.visible')
  })

  it('should display all carousel cards', () => {
    cy.visit('/')
    cy.get('#homecarousel').within(() => {
      cy.contains('Ingresá a tu cuenta').should('exist')
      cy.contains('Medios de pago').should('exist')
      cy.contains('Tiendas oficiales').should('exist')
      cy.contains('Más vendidos').should('exist')
    })
  })

  it('should navigate to login when clicking on login', () => {
    cy.visit('/')
    cy.get(':nth-child(2) > a').click({ force: true })
    cy.url().should('include', '/login')
    cy.contains('Ingresá tu e-mail o teléfono para iniciar sesión', { matchCase: false })
  })

  it('should navigate to register when clicking on register', () => {
    cy.visit('/')
    cy.get('.header-links > :nth-child(1) > a').click({ force: true })
    cy.url().should('include', '/register')
    cy.contains('Ingresá tu e-mail', { matchCase: false })

  })

  it('should refresh when clicking on logo', () => {
    cy.visit('/')
    cy.get('#logo').click({ force: true })
    cy.url().should('include', '/')
    cy.get('#adscarousel').should('be.visible')
  })
})

