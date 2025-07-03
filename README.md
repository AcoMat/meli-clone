# Seguí tus compras

Este repositorio contiene el código fuente del proyecto "Seguí tus compras", una aplicación web para la gestión de compras.

#### ... Y tambien el docker-compose para levantar todo el sistema.

---

## Integrantes
- **Acosta Matías**

---

## Funcionalidades

### Acceso Público (sin autenticación)
- Búsqueda de productos por nombre.
- Visualización de productos y sus detalles.

### Usuarios Autenticados
- **Todas las funcionalidades de acceso público.**
- **Perfil:**
    - Postear reseñas y calificaciones de productos.
    - Consulta de historial de compras.
    - Gestión de productos favoritos.
- **Carrito de compras:**
    - Agregar y eliminar productos.
    - Modificar cantidades de productos.
    - Finalización del proceso de compra.

### Administradores
- **Visualización de estadísticas:**
    - Métricas de ventas.
    - Métricas de actividad de usuarios.

---

## Frontend

El frontend está desarrollado con **React** y empaquetado con **Vite**.

### Requisitos

- **Node.js** (versión 20.x o superior)
- **npm**

### Instalación y Ejecución

1.  Navegar al directorio `frontend`:
    ```bash
    cd frontend
    ```
2.  Instalar dependencias:
    ```bash
    npm install
    ```
3.  Iniciar el servidor de desarrollo:
    ```bash
    npm run dev
    ```
La aplicación estará disponible en `http://localhost:5173`.

---

## Backend

El backend está implementado con **Spring Boot**. Provee la API REST, gestiona la lógica de negocio y la persistencia de datos.

### Requisitos

- **JDK (Java Development Kit)** (versión 21 o superior)
- **Maven** (versión 3.x o superior)

### Instalación y Ejecución

1.  Navegar al directorio `backend`:
    ```bash
    cd backend
    ```
2.  Ejecutar la aplicación:
    ```bash
    ./mvnw spring-boot:run
    ```
El servidor se iniciará en `http://localhost:8080`.

---

# Docker

El proyecto puede ser ejecutado en su totalidad utilizando Docker Compose.

### Requisitos

- **Docker**
- **Docker Compose**

### Ejecución

1.  Desde el directorio raíz del proyecto, ejecutar:
    ```bash
    docker-compose up --build
    ```
Este comando construirá las imágenes y levantará los contenedores para el frontend, backend y la base de datos MySQL.

### Variables de Entorno

Para el correcto funcionamiento de la aplicación, es necesario configurar las siguientes variables de entorno. Puedes crear un archivo `.env` en la raíz del proyecto con el siguiente contenido:

```
MERCADOLIBRE_API_CLIENT_ID=tu_client_id
MERCADOLIBRE_API_CLIENT_SECRET=tu_client_secret
MERCADOLIBRE_API_MOST_RECENT_TOKEN=tu_token
```

---

## Estructura del Repositorio

- `frontend/`: Código fuente de la aplicación React.
- `backend/`: Código fuente de la aplicación Spring Boot.