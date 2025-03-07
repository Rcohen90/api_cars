API Cars:
API sobre marcas y modelos de auto con su precio promedio.

## Construcción del proyecto

mvn clean install

## Running

mvn spring-boot:run

## Testing

- General de la API: mvn test
- Por controlador de marca: mvn -Dtest=BrandControllerTest test
- Por controlador de modelo: mvn -Dtest=ModelControllerTest test

## Data

La base de datos en memoria H2 es accesible en: http://localhost:8080/h2-console

- JDBC URL = jdbc:h2:mem:api_cars
- USERNAME = rcohen
- PASSWORD = carsapi

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3+
- Spring Data JPA
- H2 Database
- JUnit 5 & Mockito (para pruebas)
- Maven (gestor de dependencias)
