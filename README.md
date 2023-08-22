# login
Desarrollo de una Api Rest de un Login


Se implemento Spring Seurity, para el tema de seguridad

Arquitectura en Capas orientada al Dominio
Se utilizo una base de Datos relacional , Postgre
Jdk : Java 17


### Despliegue de Forma local
Descarge el proyecto en archivo zip, y extraigalo en un directorio
Abralo con un Ide
Configura la conexión a la base de datos 
* Para mayor facilidad use la base de datos remota
* Asegure que en el archivo applicatio.properties , este spring.profiles.active=pdn , que es un perfil de desarrollo
* Las variables de entorno , estan en un archivo .env , en este caso se suben al repositortio (pero esto no debe realizarse)
* Ejecute el proyecto
## PUEBAS
Se implemento swagger para una documentación basica

Hay usuario registrados en la base de datos remota, de adjunta un archivo con los datos

### Consideraciones
* Para el tema "El usuario solo debe tener una session activa" , se recomienda ingresar la segunda ves desde un navegador , para poder comprobar que el usuario no puede tener dos sessiones activas
* Las tablas de sesiones almacenas los registos de inicio y cierre de sesión
* Para bloquear el usuario debe hacer tres intentos fallidos de contraseña, se recomienda hacer estos intentos desde un navegador
* Se establecieron roles para poder ingresar a rutas especificas, el usuario "ADMIN", tiene acceso a todos los recursos
* No se puede guardar otro usuario, que ya tiene asignado una persona 


