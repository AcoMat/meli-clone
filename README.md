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
- **Variables de Entorno**

### **Importante**
Para el correcto funcionamiento de la aplicación, es necesario configurar
la siguiente variable de entorno.
En un archivo `.env` en la raíz del proyecto poner el siguiente contenido:
```
MERCADOLIBRE_API_MOST_RECENT_TOKEN=tu_token
```

### Ejecución

1. Ir al directorio /backend y ejecutar:
    ```bash
    mvn clean package -DskipTests
    ```
   
2. Desde el directorio raíz del proyecto, ejecutar:
    ```bash
    docker-compose -f docker-compose.dev.yml up --build
    ```
Este comando construirá las imágenes y levantará los contenedores para el frontend, backend y la base de datos MySQL.

---

### Datos iniciales
Para facilitar la visualización de la aplicación, se han incluido datos iniciales en la base de datos. Estos datos incluyen productos, usuarios y reseñas predefinidos.

**usuarios:**

| Rol     | Email                        | Contraseña   |
|---------|------------------------------|--------------|
| admin   | admin@email.com              | admin123     |
| cliente | juliantrejo@email.com        | julian123    |
| cliente | douglasespagnol@email.com    | douglas123   |
| cliente | juancruzcenturion@email.com  | juan123      |
| cliente | lucasdellagiustina@email.com | lucas123     |
| cliente | juanignaciogarcia@email.com  | juan123      |
| cliente | urielpiñeyro@email.com       | uriel123     |
| cliente | matiaslaime@email.com        | matias123    |
| cliente | juancabezas@email.com        | juan123      |
| cliente | agueromauro@email.com        | aguero123    |
| cliente | aguerofernando@email.com     | aguero123    |
| cliente | mailinsoñez@email.com        | mailin123    |
| cliente | carlossaldaña@email.com      | carlos123    |
| cliente | adriancardozo@email.com      | adrian123    |
| cliente | sofiarossi@email.com         | sofia123     |
| cliente | mateodiaz@email.com          | mateo123     |
| cliente | valentinagomez@email.com     | valentina123 |
| cliente | benjamincastro@email.com     | benjamin123  |
| cliente | isabellahernandez@email.com  | isabella123  |
