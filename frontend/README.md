# Segui Tus Compras - Frontend

Una aplicación frontend moderna de un ecommerce hecha en React y construida con Vite, que proporciona una experiencia de compra integral con autenticación de usuarios, navegación de productos, gestión de carrito y funcionalidades administrativas.

## Stack Tecnológico

- **Framework**: React 18.3.1
- **Herramienta de Build**: Vite 5.4.1
- **Estilos**: Bootstrap 5.3.3
- **Enrutamiento**: React Router DOM 6.26.2
- **Cliente HTTP**: Axios 1.7.7
- **Testing**: Cypress 14.4.0
- **Servidor Web**: Nginx (para producción)

## Características

### Funcionalidades de Usuario
- Registro y autenticación de usuarios
- Navegación y búsqueda de productos
- Gestión del carrito de compras
- Funcionalidad de favoritos/lista de deseos
- Historial de compras
- Reseñas y calificaciones de productos
- Gestión de perfil de usuario

### Funcionalidades Administrativas
- Panel de gestión de usuarios
- Análisis de ventas y reportes
- Interfaz de gestión de productos

### Características Técnicas
- Diseño responsivo con Bootstrap
- Arquitectura de Aplicación de Página Única (SPA)
- Autenticación basada en JWT
- Gestión de estado con React Context
- Soporte para containerización con Docker
- Configuración de proxy reverso con Nginx

## Requisitos Previos

- Node.js 20 o superior
- Gestor de paquetes npm o yarn
- Docker (para despliegue containerizado)

## Variables de Entorno

La aplicación requiere las siguientes variables de entorno:

### Variables Requeridas

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `VITE_BACKEND_URL` | URL base de la API del backend | `http://localhost:8080/` |

### Configuración de Entorno

Crear un archivo `.env` en el directorio raíz:

```
VITE_BACKEND_URL=http://localhost:8080/
```

Para el despliegue en producción, asegúrese de que `VITE_BACKEND_URL` apunte al endpoint de la API de producción.

## Instalación y Configuración

### Desarrollo Local

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd frontend
   ```

2. **Instalar dependencias**
   ```bash
   npm install
   ```

3. **Configurar variables de entorno**
   ```bash
   cp .env.example .env
   # Editar .env con su configuración
   ```

4. **Iniciar servidor de desarrollo**
   ```bash
   npm run dev
   ```

La aplicación estará disponible en `http://localhost:5173`


## Estructura del Proyecto

```
src/
├── assets/                 # Recursos estáticos (imágenes, iconos, etc.)
├── components/            # Componentes React reutilizables
│   ├── admin/            # Componentes específicos de administración
│   ├── basic/            # Componentes básicos de UI
│   ├── cards/            # Componentes de tarjetas
│   ├── carousels/        # Componentes de carrusel
│   ├── cart/             # Componentes del carrito de compras
│   ├── forms/            # Componentes de formularios
│   ├── layout/           # Componentes de layout
│   ├── product/          # Componentes relacionados con productos
│   ├── search/           # Componentes de búsqueda
│   └── user/             # Componentes de perfil de usuario
├── context/              # Proveedores de React Context
├── hooks/                # Hooks personalizados de React
├── pages/                # Componentes de páginas
│   ├── admin/           # Páginas de administración
│   ├── auth/            # Páginas de autenticación
│   ├── product/         # Páginas de productos
│   ├── purchase/        # Páginas del flujo de compra
│   └── user/            # Páginas de perfil de usuario
├── services/            # Servicios de API y utilidades
└── util/                # Funciones de utilidad
```

## Testing

### Testing End-to-End con Cypress

```bash
# Abrir Cypress Test Runner
npx cypress open

# Ejecutar tests en modo headless
npx cypress run
```
Los archivos de test se encuentran en el directorio `cypress/e2e/`.

## Imágenes

*TODO*
