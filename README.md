# StoreSpring

## Overview

This Spring Boot application serves as a backend simulation for a store. It provides a foundation for studying and understanding the implementation of key backend functionalities typically found in a store management system.

## Features

- **Product Management**: Operations for managing products, including details such as name, and price.

- **Order Processing**: Ability to place and process customer orders.

- **User Authentication**: Secure endpoints with user authentication for sensitive operations.

- **RESTful API**: The application exposes a set of RESTful APIs to interact with the backend services.

## Technologies Used

- **Spring Boot**: Framework for building Java-based enterprise applications.

- **Spring Data JPA**: Simplifies database access using Java Persistence API.

- **Spring Security**: Provides authentication and access control to secure endpoints.

- **H2 Database**: In-memory database for development and testing.

- **Swagger**: API documentation tool for easy exploration and testing of APIs.

## Profiles

### Development (dev) Profile

The `dev` profile is configured to start the H2 in-memory database with sample data for development purposes. This profile is useful for local development and testing.

### Test (test) Profile

The `test` profile is designed for running automated tests. It ensures that the application behaves correctly in a controlled testing environment.

To run tests with the `test` profile:

```bash
mvn test -P test
```

## Getting Started

### Prerequisites

- Java 17 or higher installed.

- Maven build tool.

### Build and Run

1. Clone the repository:

   ```bash
   git clone https://github.com/LuizVictor/spring-store.git
   ```

2. Navigate to the project directory:

   ```bash
   cd spring-store
   ```

3. Build the project:

   ```bash
   mvn clean install -DskipTests
   ```

4. Run the application:

   ```bash
   java -jar -Dspring.profiles.active=dev target/spring-store-0.0.1-SNAPSHOT.jar
   ```

The application will be accessible at [http://localhost:8080](http://localhost:8080).

## API Documentation

Swagger is integrated into the application for easy API exploration and testing. Once the application is running, you can access the Swagger UI at:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

This interface allows you to interactively explore and test the available API endpoints.

## Security

- The application uses Spring Security for securing endpoints. Make sure to authenticate before accessing protected resources.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests.
