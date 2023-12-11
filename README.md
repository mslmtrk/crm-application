# CRM Application
[![Spring-Cloud-Config-Server CI/CD](https://github.com/mslmtrk/crm-application/actions/workflows/spring-cloud-config-server.yml/badge.svg)](https://github.com/mslmtrk/crm-application/actions/workflows/spring-cloud-config-server.yml)
[![Naming-Server CI/CD](https://github.com/mslmtrk/crm-application/actions/workflows/naming-server.yml/badge.svg)](https://github.com/mslmtrk/crm-application/actions/workflows/naming-server.yml)
[![CRM-Application-Backend CI/CD](https://github.com/mslmtrk/crm-application/actions/workflows/crm-application-backend.yml/badge.svg)](https://github.com/mslmtrk/crm-application/actions/workflows/crm-application-backend.yml)
#### In this application users can log in and sign-up. Authenticated users can operate CRUD operations on customers.
#### The App is hosted on AWS: [Live Application](http://crm-frontend.eu-central-1.elasticbeanstalk.com), [Live API Documentation](http://crm-backend.eu-central-1.elasticbeanstalk.com/api/swagger-ui/index.html).

![Screenshot_10](https://github.com/mslmtrk/crm-application/assets/60064079/3fa4948b-e89b-4da3-87cf-b6cef041e5d1)

#### This application consists of four microservices:
- CRM-Backend: A REST API that is on the backend side of the application.
- CRM-Frontend: The fronted side of the application that is built with Thymleaf.
- Naming-Server: Eureka naming server for service registry, discovery, and load balancing.
- Spring Cloud Config Server: For centralized configuration management.

## Technologies
#### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Cloud
- JWT
- Hibernate
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- OpenAPI SpringDoc
- Resilience4j
#### Frontend
- Spring MVC
- Thymeleaf
- JavaScript
- HTML, CSS
- Bootstrap
- Hibernate Validator
- OpenFeign
#### Other
- Zipkin
- Docker
  
## How to Run
1. Just clone the repository and run `docker compose up`. Make sure Docker Desktop is up and running.
2. The running app: http://localhost:8081
3. The API documentation: http://localhost:8080/api/swagger-ui/index.html
