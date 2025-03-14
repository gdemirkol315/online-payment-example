# Online Store Payment Integration with Klarna

This Spring Boot application provides REST APIs for integrating with Klarna Payments API for online store payment processing.

## Features

- Create payment sessions
- Update payment sessions
- Get session details
- Create orders
- Cancel authorizations
- Generate customer tokens for future purchases

## Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring WebFlux for reactive API calls
- Lombok for reducing boilerplate code
- SpringDoc OpenAPI for API documentation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Configuration

Configure the application by updating the `application.properties` file:

```properties
# Klarna API configuration
klarna.api.url=https://api.klarna.com
klarna.api.username=your_api_username
klarna.api.password=your_api_password
klarna.api.merchant.id=your_merchant_id
```

### Building the Application

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080 with context path `/api`.

## API Documentation

Once the application is running, you can access the API documentation at:

- Swagger UI: http://localhost:8080/api/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api/api-docs

## API Endpoints

### Payment Sessions

- `POST /api/klarna/sessions` - Create a new payment session
- `GET /api/klarna/sessions/{sessionId}` - Get details about a session
- `POST /api/klarna/sessions/{sessionId}` - Update a session

### Orders and Authorizations

- `POST /api/klarna/authorizations/{authorizationToken}/order` - Create an order
- `DELETE /api/klarna/authorizations/{authorizationToken}` - Cancel an authorization
- `POST /api/klarna/authorizations/{authorizationToken}/customer-token` - Generate a customer token

## Klarna API Documentation

For more information about the Klarna Payments API, refer to the [official documentation](https://docs.klarna.com/klarna-payments/).

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
