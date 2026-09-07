# Veltrix API

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9%2B-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Swagger](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D?logo=swagger&logoColor=black)](https://swagger.io/tools/swagger-ui/)

API backend de **Veltrix**, una tienda de venta de calzado. El proyecto expone servicios REST para administrar el catálogo, usuarios, clientes, pedidos, pagos y el proceso de checkout.

## Descripción general

Veltrix API es una aplicación RESTful desarrollada con Spring Boot. Sus principales capacidades son:

- Gestión de categorías y productos del catálogo.
- Registro, autenticación y consulta del usuario actual.
- Emisión y validación de tokens JWT.
- Administración de usuarios, clientes y roles.
- Gestión de pedidos, detalles de pedido y pagos.
- Procesamiento de checkout a partir del carrito de compra.
- Documentación interactiva mediante OpenAPI y Swagger UI.

La API utiliza autenticación stateless. Las operaciones protegidas reciben el token mediante el encabezado `Authorization: Bearer <token>`.

## Proyectos relacionados

- [Veltrix Web](../veltrix-web): aplicación frontend relacionada para la experiencia de compra y consumo de esta API.

> Si el frontend se encuentra en otro repositorio remoto, sustituye el enlace relativo por la URL pública correspondiente.

## Stack tecnológico

| Tecnología | Uso |
| --- | --- |
| Java 21 | Lenguaje y runtime de la aplicación |
| Spring Boot 4.0.6 | Framework principal y configuración de la aplicación |
| Spring Web MVC | Creación de controladores y endpoints REST |
| Spring Security | Autenticación, autorización y protección de recursos |
| JJWT 0.12.5 | Generación y validación de tokens JWT |
| Spring Data JPA | Persistencia y acceso a datos mediante repositorios |
| Hibernate | Implementación ORM utilizada por JPA |
| H2 Database | Base de datos local persistida en archivo |
| Lombok | Reducción de código repetitivo en modelos y DTOs |
| SpringDoc OpenAPI | Generación de la especificación OpenAPI y Swagger UI |
| Maven Wrapper | Compilación, pruebas y ejecución reproducible |

## Requisitos previos

- JDK 21 o superior.
- Maven 3.9 o superior, opcional si se utiliza el Maven Wrapper incluido.
- Git, para clonar el repositorio.

El proyecto incluye `mvnw` y `mvnw.cmd`, por lo que no es necesario instalar Maven globalmente.

## Configuración de base de datos y propiedades

La aplicación utiliza H2 en modo archivo. La conexión está configurada directamente en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/veltrixdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

La base de datos se crea o actualiza en la carpeta `data/` del proyecto. Hibernate mantiene el esquema mediante:

```properties
spring.jpa.hibernate.ddl-auto=update
```

También se habilita la consola web de H2 en:

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Para cambiar de base de datos, modifica directamente la URL y las credenciales en `application.properties` y agrega el driver correspondiente al `pom.xml`.

### Configuración JWT

La clave y la duración del token se encuentran actualmente en `application.properties`:

```properties
jwt.secret=VeltrixProyectoSena2026ClaveSuperSeguraJWT
jwt.expiration=86400000
```

En entornos de producción, la clave debe trasladarse a variables de entorno o a un gestor de secretos. No se recomienda mantener secretos reales versionados en el repositorio.

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd veltrix
```

### 2. Compilar el proyecto

En Windows:

```powershell
.\mvnw.cmd clean compile
```

En Linux o macOS:

```bash
./mvnw clean compile
```

Si Maven está instalado globalmente, también puedes ejecutar:

```bash
mvn clean compile
```

### 3. Compilar las pruebas

```bash
# Windows
.\mvnw.cmd clean test-compile

# Linux/macOS
./mvnw clean test-compile
```

### 4. Ejecutar las pruebas

```bash
# Windows
.\mvnw.cmd test

# Linux/macOS
./mvnw test
```

### 5. Iniciar la aplicación

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

La aplicación se inicia por defecto en `http://localhost:8080`.

### Comandos equivalentes con Gradle

Este repositorio utiliza Maven y no incluye `gradlew`. Si se incorpora una configuración Gradle equivalente, los comandos habituales serían:

```bash
./gradlew clean build
./gradlew test
./gradlew bootRun
```

## Documentación de la API

Con la aplicación iniciada, abre Swagger UI en:

**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

La especificación OpenAPI está disponible en:

- [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

En Swagger UI puedes utilizar el botón **Authorize** y enviar un JWT con el formato:

```text
Bearer <token>
```

## Endpoints principales y seguridad

### Endpoints públicos

| Método | Ruta | Descripción |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Registra un nuevo usuario. |
| `POST` | `/api/auth/login` | Valida las credenciales y devuelve un JWT. |
| `GET` | `/api/productos` | Lista los productos del catálogo. |
| `GET` | `/api/productos/{id}` | Consulta un producto por identificador. |
| `GET` | `/swagger-ui.html` | Abre la documentación interactiva. |
| `GET` | `/v3/api-docs` | Devuelve la especificación OpenAPI. |
| `GET` | `/h2-console` | Abre la consola H2 local. |

### Endpoints protegidos

Los siguientes recursos requieren un token Bearer válido:

| Recurso | Acceso |
| --- | --- |
| `/api/auth/me` | Usuario autenticado |
| `/api/pedidos/**` | Roles `ADMIN` o `CLIENTE` |
| `/api/detalles-pedido/**` | Roles `ADMIN` o `CLIENTE` |
| `/api/pagos/**` | Roles `ADMIN` o `CLIENTE` |
| `/api/usuarios/**` | Rol `ADMIN` |
| `/api/categorias/**` | Rol `ADMIN` |
| `POST`, `PUT` y `DELETE /api/productos/**` | Rol `ADMIN` |
| `/api/clientes/**` | Autenticación requerida por la regla general |

Ejemplo de solicitud autenticada:

```bash
curl -H "Authorization: Bearer <token>" \
     http://localhost:8080/api/auth/me
```

### Operaciones de pedidos

El recurso `/api/pedidos` incluye operaciones CRUD y el flujo de checkout:

```text
POST /api/pedidos/checkout
```

El checkout recibe los productos del carrito, el método de pago y, opcionalmente, la dirección de envío. La respuesta incluye el identificador del pedido creado y un mensaje de confirmación.

## Estructura del proyecto

```text
veltrix/
├── data/
│   └── veltrixdb.mv.db
├── src/
│   ├── main/
│   │   ├── java/com/veltrix/
│   │   │   ├── config/              # Configuración OpenAPI
│   │   │   ├── controller/          # Endpoints REST
│   │   │   ├── dto/                 # Objetos de transferencia
│   │   │   │   ├── auth/             # DTOs de autenticación
│   │   │   │   └── checkout/         # DTOs del carrito y checkout
│   │   │   ├── enums/               # Roles y estados del dominio
│   │   │   ├── exception/           # Excepciones y manejadores globales
│   │   │   ├── mapper/              # Conversión entre entidades y DTOs
│   │   │   ├── model/               # Entidades JPA del dominio
│   │   │   ├── repository/          # Repositorios Spring Data JPA
│   │   │   ├── security/             # Seguridad, autenticación y JWT
│   │   │   └── service/              # Lógica de negocio
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/java/                   # Pruebas automatizadas
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Flujo de autenticación

1. El cliente envía sus credenciales a `POST /api/auth/login`.
2. La API valida el correo y la contraseña.
3. La API devuelve un token JWT firmado.
4. El cliente envía el token en cada solicitud protegida:

   ```http
   Authorization: Bearer <token>
   ```

5. El filtro JWT valida el token y establece la identidad y el rol del usuario.
6. Spring Security autoriza o rechaza la operación según el recurso solicitado.

## Desarrollo y mantenimiento

Para generar un artefacto ejecutable:

```bash
# Windows
.\mvnw.cmd clean package

# Linux/macOS
./mvnw clean package
```

El archivo generado se encuentra en `target/`.

Antes de abrir un pull request, comprueba al menos:

```bash
# Windows
.\mvnw.cmd clean test-compile
.\mvnw.cmd test

# Linux/macOS
./mvnw clean test-compile
./mvnw test
```

## Licencia

Este proyecto no declara actualmente una licencia de distribución en su configuración Maven.
