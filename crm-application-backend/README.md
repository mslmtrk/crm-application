# CRM Application Backend
#### A REST API that provides functionalities such as login, signup and CRUD operations to [the frontend side](https://github.com/mslmtrk/Crm-Application-Frontend) of the application. It has been deployed to AWS.

#### Live App: http://crm-frontend.eu-central-1.elasticbeanstalk.com/

#### Live Api Documentation: http://crm-backend.eu-central-1.elasticbeanstalk.com/api/swagger-ui/index.html

## Technologies
- Java 17
- Spring Boot
- Spring Security
- Hibernate
- Spring Data JPA
- Maven
- JWT
- MySQL
- OpenAPI SpringDoc

## To run on your own computer
1. Install MySQL.
2. Configure datasource credentials in application.properties.
3. Open two console in the path of the backend and the frontend app.
4. Run `mvnw install` or `./mvnw install` in Unix System.
5. Run `mvnw spring-boot:run` or `./mvnw spring-boot:run` in Unix System.
6. The frontend app is running on localhost:8081, and the backend app is running on localhost:8080.
