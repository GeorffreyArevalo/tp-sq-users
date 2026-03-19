

# Instrucciones

## Stack Tecnológico y Paradigma
- Stack: Spring Boot `4.0.3` + Gradle wrapper `9.3.1` + Java versión `25` (see `build.gradle`, `gradle/wrapper/gradle-wrapper.properties`) + Postgresql (Base de datos a utilizar) + MapStruct (Para convertir objetos de dominio y dtos) + Spring Data JPA (ORM para la persistencia de la Base de Datos) + Lombok(Generación de Código - NO usar en la capa de Dominio) + OAuth 2.0 JWT (Para la seguridad del Sistema, implementar cuando se le indique).
- No configurar las variables de entorno en el application.yml.
- El paradigma a utilizar es basado en Programación Funcional

## Arquitectura (Arquitectura Hexagonal / Puertos y Adaptadores)
- Paquete Base: `src/main/java/com/pragmafood/talentpool/users`.
- `domain/` Reservado para la lógica de negocio, se definen las reglas de negocio modelos e interfaces, la capa debe ser agnóstica a las tecnologías de terceros, en esta capa solo debe existir código java en la versión 25, compatible con lo que tiene el JDK:
  - `api/` (interfaces que definen las reglas de los casos de uso, para cada caso de uso debe existir una), `usecases/` (Servicio de dominio, el cual contiene toda la lógica gruesa de la aplicación), `models/` (Modelos representativos de la aplicación, define las reglas que cada entidad debe cumplir dentro del dominio), `spi/` (Puerros de salida, son las interfaces que definen las reglas para adaptar el código de terceros, dentro de este paquete puede exister el paquete 'persistence', el cual contiene las interfaces correspondiente  a la persistencia de la base de datos), `exception/` (Contiene las excepciones personalizadas, las cuales manejas las excepciones de negocio, deben ser excepciones unckecked).
- `application/` es la capa reservada para la orquestación de los casos de uso y de recibir la petición de los controllers:
  - `handlers/` (Contiene los servicios de Spring Boot, los cuales se comunican con los casos de uso), `dtos/` (contiene los request y los responses, los cuales representan los datos de entrada y las respuetas de las peticiones, en lo posible utilizar records y que no llamarlos al final Dto si no Request o Response según lo que represente), `mappers/` (Interfaces de MapStruct, encargadas de convertir los modelos de negocio en Response y los Request en modelos de negocio).
- `infrastructure/` Reservado para la adaptación del código de terceros:
  - `input/rest/` (Controllers de la aplicación, los encargados de recibir las peticiones entrantes), `output/persistence/` (Adaptación de toda la capa de persistencia, entities para las entidades de JPA, repositories para los repositories de JPA, mappers para las interfaces de mapstruct para convertir de los modelos de dominio a entidades de JPA y viceversa, adapters para adaptar las interfaces de spi correspondientes solo a la persistencias), `output/` (Existe todas las adaptaciones de códigos de terceros), `config/` (Configuración necesaria de Spring Boot, clases de configuración y creación de beans necesarios).
- Estructura jerárquica: Las dependencias deben seguir el patrón de inyección de dependencias, además se debe cumplir con las definiciones de los principios solid (infrastructure -> application/domain, not the reverse).

## Build, Test, Run Workflow
- Todas las clases deben tener realizadas sus pruebas unitarias respectivas, cumpliendo los principios de atomicidad

# Documentación: 
- Todos los endpoints deben estar documentados son OpenAPI.

## Dependency and Integration Notes
- Web/API capas basado en servlet y con programación funcional (`spring-boot-starter-web`), no WebFlux.
- JPA + Postgresql para la persistencia de los datos (`spring-boot-starter-data-jpa`, `org.postgresql:postgresql`).
- Validation para la validación de los Request, es decir del cuerpo de la implementación NO utilizar en la capa de dominio (`spring-boot-starter-validation`).


## Instrucciones adicionales
- Cada una de las peticiones, seguir adecuadamente el requerimiento y los criterios de aceptación
- Todos los endpoints deben cumplir con el estándar de buenas prácticas del protocolo HTTP 
- Todo el código generado debe estar en el Idioma Ingles.