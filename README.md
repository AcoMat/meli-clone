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

# Docker

El proyecto puede ser ejecutado en su totalidad utilizando Docker Compose.

### Requisitos

- **Docker**
- **Docker Compose**

### Ejecución

1Ir al directorio /backend y ejecutar:
    ```bash
    mvn clean package
    ```
   
2Desde el directorio raíz del proyecto, ejecutar:
    ```bash
    docker-compose -f docker-compose.dev.yml up --build
    ```
Este comando construirá las imágenes y levantará los contenedores para el frontend, backend y la base de datos MySQL.

### Variables de Entorno

Para el correcto funcionamiento de la aplicación, es necesario configurar 
las siguientes variables de entorno. 
En un archivo `.env` en la raíz del proyecto poner el siguiente contenido:

```
MERCADOLIBRE_API_CLIENT_ID=tu_client_id
MERCADOLIBRE_API_CLIENT_SECRET=tu_client_secret
MERCADOLIBRE_API_MOST_RECENT_TOKEN=tu_token
```

---