# API RESTfull Usuario (`Usuario API`)

Evaluacion de conocimientos en Spring Boot y Java.

## Tecnologias Ocupadas
- **Spring Boot**.s
- **Maven**.
- **Swagger**.
- **HSQLBD**.
- **JPA - Hibernate**.
- **Lombok**.
- **JWT**.
- **Postman**.
- **Git**.
- **IntelliJ IDEA**.

## Funciones de la API

- **Crear Usuario - POST** - `http://localhost:8081/usuarios/`.
- **Eliminar Usuario - DELETE** - `http://localhost:8081/usuarios/${{ID}}`.
- **Actualizar Usuario - UPDATE** `http://localhost:8081/usuarios/${{ID}}`.
- **Obtener Todos Los Usuarios - GET** `http://localhost:8081/usuarios/todos/`.
- **Obtener usuario por ID - GET** `http://localhost:8081/usuarios/${{ID}}`.

Tambien se puede usar el archivo  [`Usuario.postman_collection.json`](https://github.com/PaarXul/API-USUARIOS/blob/main/src/main/resources/Usuario.postman_collection.json) para importar las peticiones a Postman.

## Estructura del Proyecto

- **src/main/java/com/usuarios/apirest:** Contiene la clase principal de la aplicación, la cual es `ApiRestApplication.java`. Esta clase es la encargada de ejecutar la aplicación.
- **src/main/java/com/usuarios/apirest/config:** Contiene la clase `SwaggerConfig.java`, la cual es la encargada de configurar Swagger.
- **src/main/java/com/usuarios/apirest/config/jwt:** Contiene la clase `JwtUtil.java`, la cual es la encargada de generar el token.
- **src/main/java/com/usuarios/apirest/controller:** Contiene la clase `UsuarioController.java`, la cual es la encargada de manejar las peticiones HTTP.
- **src/main/java/com/usuarios/apirest/dto:** Contiene las clases `UsuarioDto.java` y `UsuarioResponseDto.java`, las cuales son las encargadas de representar los datos de un usuario.
- **src/main/java/com/usuarios/apirest/model:** Contiene la clase `Usuario.java` y `Telefonos.java`, la cual es la encargada de representar la entidad Usuario.
- **src/main/java/com/usuarios/apirest/repository:** Contiene la interfaz `UsuarioRepository.java`, la cual es la encargada de manejar las operaciones de la base de datos.
- **src/main/java/com/usuarios/apirest/service:** Contiene la interfaz `UsuarioService.java`, la cual es la encargada de manejar la lógica de negocio. También contiene la clase `UsuarioServiceImpl.java`, la cual implementa la interfaz `UsuarioService.java`.
- **src/main/resources:** Contiene el archivo `application.properties`, el cual es el encargado de configurar la aplicación.
- **pom.xml:** Contiene las dependencias del proyecto.
- **README.md:** Contiene la información del proyecto.


## Diagrama de Solución
![Diagrama de Solucion](src/main/resources/DiagramaSolucion.png)


## Métodos

- **guardarUsuario:** Este método está asociado con la ruta POST `/usuarios/`. Recibe un `UsuarioDto` como el cuerpo del request y luego utiliza `usuarioService` para guardar el estado del usuario. Retorna un `UsuarioResponseDto` dentro de un `ResponseEntity`.

- **obtenerUsuario:** Este método está asociado con la ruta GET `/usuarios/{usuarioId}`. Recibe el `usuarioId` como un parámetro de la ruta y utiliza `usuarioService` para obtener la información del usuario. Retorna una instancia de `Usuario` dentro un `ResponseEntity`.

- **eliminarUsuario:** Este método está asociado con la ruta DELETE `/usuarios/{usuarioId}`. Recibe el `usuarioId` como un parámetro de la ruta y utiliza `usuarioService` para eliminar al usuario.

- **obtenerUsuarios:** Este método está asociado con la ruta GET `/usuarios/todos/`. No recibe ningún parámetro y usa el `usuarioService` para obtener todos los usuarios. Retorna un conjunto de Usuarios (`Set<Usuario>`) dentro de un `ResponseEntity`.

- **actualizarUsuario:** Este método está asociado con la ruta PATCH `/usuarios/{usuarioId}`. Recibe el `usuarioId` como un parámetro de ruta y un `UsuarioDto` como el cuerpo del request. Utiliza `usuarioService` para actualizar el estado del usuario y retorna un `UsuarioResponseDto` dentro de un `ResponseEntity`.

## Uso de la API

- 1° Clonar el repositorio en su maquina local. https://github.com/PaarXul/API-USUARIOS.git
- 2° Abrir el proyecto en su IDE de preferencia.
- 3° Ejecutar el proyecto con el comando `mvn spring-boot:run`.
- 4° Abrir el navegador y dirigirse a la siguiente ruta: `http://localhost:8081/swagger-ui.html#/`.
- 5° Probar los diferentes endpoints de la API.
  - a) Para crear un usuario, dirigirse al endpoint `POST /usuarios/` y hacer click en el botón `Try it out`. Luego, ingresar los datos del usuario en el cuerpo del request y hacer click en `Execute`.
  - b) Para obtener un usuario, dirigirse al endpoint `GET /usuarios/{usuarioId}` y hacer click en el botón `Try it out`. Luego, ingresar el `usuarioId` en el campo `usuarioId` y hacer click en `Execute`.
  - c) Para eliminar un usuario, dirigirse al endpoint `DELETE /usuarios/{usuarioId}` y hacer click en el botón `Try it out`. Luego, ingresar el `usuarioId` en el campo `usuarioId` y hacer click en `Execute`.
  - d) Para obtener todos los usuarios, dirigirse al endpoint `GET /usuarios/todos/` y hacer click en el botón `Try it out`. Luego, hacer click en `Execute`.
  - e) Para actualizar un usuario, dirigirse al endpoint `PUT /usuarios/{usuarioId}` y hacer click en el botón `Try it out`. Luego, ingresar el `usuarioId` en el campo `usuarioId` y los datos del usuario en el cuerpo del request. Finalmente, hacer click en `Execute`.

