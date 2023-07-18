# Crm-Application-Frontend
Frontend side of the Crm App, uses [crm-app-backend](https://github.com/mslmtrk/Crm-Application-Backend) for data transactions. 
It may take a few seconds to startup the application.

Live App: https://crm-app-frontend.herokuapp.com/

Live Crm-App-Rest documentation: https://crm-app-rest.herokuapp.com/swagger-ui.html

![Screenshot_1](https://user-images.githubusercontent.com/60064079/179352987-99b0cc08-90df-404b-8a10-bd3329bb5613.png)

## Technologies
**Backend**
- Java 11
- Spring Boot
- Spring Security
- Hibernate
- JWT Authentication
- Spring Data JPA
- MySQL(PostgreSQL on Heroku)

**Frontend**
- Java 11
- Spring MVC
- Thymeleaf
- Java Bean Validation
- JavaScript
- Bootstrap

## To run on your own computer
1. Install MySQL
2. Configure datasource credentials in crm-app-backend/src/main/resources/application.properties
3. Open two console in the path of the backend and the frontend app
4. Run `mvn install`
5. Run `mvn spring-boot:run`
6. The frontend app running on localhost:8081 and the backend app running on localhost:8080
