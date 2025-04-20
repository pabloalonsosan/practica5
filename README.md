# P5
Aplicación web que usa Spring JPA para persistir los datos de un API REST de gestión de usuarios.
El API permite el registro de nuevos usuarios y su identificación mediante email y password.
Una vez identificados, se emplea una cookie de sesión para autenticar las peticiones que permiten 
a los usuarios leer, modificar y borrar sus datos. También existe un endpoint para cerrar la sesión.  

## Endpoints

// TODO#1: rellena la tabla siguiente analizando el código del proyecto

| Método                    | Ruta                  | Descripción                            | Respuestas                                 |
|---------------------------|-----------------------|----------------------------------------|--------------------------------------------|
| POST                      | /api/users            | Registrar un nuevo usuario             |      Created                 Conflict                      |
| POST                      | /api/users/me/session | Iniciar sesión (login)	                |      Created                 Unauthorized                     |
| DELETE                    | /api/users/me/session | Cerrar sesión (logout)	                |  No Content       Unauthorized                                   |
|GET                        | /api/users/me	        | Obtener perfil del usuario autenticado	|  OK          Unauthorized                                |
|PUT                        | /api/users/me	        | Modificar perfil del usuario           |  OK          Unauthorized                               |
|DELETE                     | /api/users/me	        | Eliminar usuario autenticado           |    No Content       Unauthorized                                 |


## Comandos 

- Construcción: 
  ```sh
  ./mvnw clean package
  ```

- Ejecución: 
  ```sh
  ./mvnw spring-boot:run
  ```

- Tests:
  ```sh
  ./mvnw test
  ```
