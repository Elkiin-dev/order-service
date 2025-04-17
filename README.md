# Order Service

This project is a **microservice** built with **Spring Boot**, following **Clean Architecture** principles and using **JPA** for data persistence. It clearly separates responsibilities into `application`, `domain`, and `infrastructure` layers, and uses various technologies and tools to ensure performance, scalability, and long-term maintainability.

## Architecture

The project follows **Clean Architecture** principles, organizing the code into layers:
- **Application**: Contains use cases and application logic.
- **Domain**: Defines business models and rules.
- **Infrastructure**: Implements persistence, controllers, and configurations.

It is also part of a **microservices** ecosystem, making it scalable and maintainable.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database**

## Installation and Execution

### 1. Clone the repository
```sh
git clone https://github.com/Elkiin-dev/order-service.git
```

### 2. Build the module
```sh
./gradlew clean build
```

### 3. Create the Docker image
```sh
docker build -t order-service .
```

### 4. Start the Docker container
```sh
docker run -p 8080:8080 --name order-service order-service
```

API endpoints are exposed at `http://localhost:8080`.

## Endpoints

---
### *Category*
### `GET /categories`

#### Description
Fetches all available categories.

#### Responses

| Code  | Description                  | Content                                   |
|-------|------------------------------|-------------------------------------------|
| `200` | List of categories retrieved | `application/json`: `Category[]`          |
| `500` | Unexpected error             | `application/json`: Internal server error |

#### Example response (`200`)
```json
[
  {
    "id": "3f142010-f289-11ee-a13b-7b20d68db3f1",
    "name": "Clothing",
    "parent": null
  }
]
```

---

### `GET /categories/{id}`

#### Description
Retrieves a category by its ID.

#### Parameters

| Parameter | Description              | Required | Type  |
|-----------|--------------------------|----------|-------|
| `id`      | UUID of the category     | Yes      | UUID  |

#### Responses

| Code   | Description                 | Content                      |
|--------|-----------------------------|------------------------------|
| `200`  | Category found              | `application/json`: Category |
| `404`  | Category not found          | `application/json`: Error    |

---

### `POST /categories`

#### Description
Creates a new category.

#### Body
```json
{
  "name": "Home",
  "parentId": "optional-uuid"
}
```

#### Responses

| Code   | Description                         | Content                        |
|--------|-------------------------------------|--------------------------------|
| `200`  | Category successfully created       | `application/json`: empty     |
| `400`  | Category name already exists        | Error                         |
| `404`  | Parent category not found           | `application/json`: Error     |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### `DELETE /categories/{id}`

#### Description
Deletes a category.

#### Parameters

| Parameter | Description           | Required | Type  |
|-----------|-----------------------|----------|--------|
| `id`      | UUID of the category  | Yes      | UUID   |

#### Responses

| Code   | Description               | Content                      |
|--------|---------------------------|------------------------------|
| `204`  | Category deleted          | No Content                   |
| `404`  | Category not found        | `application/json`: Error    |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### *Products*
### `GET /products`

#### Description
Retrieves all products.

#### Responses

| Code   | Description                | Content                     |
|--------|----------------------------|-----------------------------|
| `200`  | List of products retrieved | `application/json`: Product[] |
| `500`  | Internal server error      | `application/json`: Error   |

---

### `GET /products/{id}`

#### Description
Retrieves a product by ID.

| Parameter | Description         | Required | Type  |
|-----------|---------------------|----------|--------|
| `id`      | UUID of the product | Yes      | UUID   |

#### Responses

| Code   | Description           | Content                   |
|--------|-----------------------|---------------------------|
| `200`  | Product found         | `application/json`: Product |
| `404`  | Product not found     | `application/json`: Error   |

---

### `GET /products/category/{categoryId}`

#### Description
Fetches all products under a given category.

| Parameter     | Description             | Required | Type  |
|---------------|-------------------------|----------|--------|
| `categoryId`  | UUID of the category    | Yes      | UUID   |

#### Responses

| Code   | Description                   | Content                        |
|--------|-------------------------------|--------------------------------|
| `200`  | Products found                | `application/json`: Product[] |
| `404`  | Category not found            | `application/json`: Error     |

---

### `POST /products`

#### Description
Creates a new product.

#### Body
```json
{
  "name": "T-shirt",
  "price": 25.00,
  "categoryId": "3f142010-f289-11ee-a13b-7b20d68db3f1",
  "image": "url-image.png",
  "stock": 100
}
```

#### Responses

| Code   | Description                       | Content                   |
|--------|-----------------------------------|---------------------------|
| `200`  | Product successfully created      | Empty                     |
| `400`  | Product name already exists       | `application/json`: Error |
| `404`  | Category not found                | `application/json`: Error |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### `PATCH /products/{id}/stock?stock=20`

#### Description
Updates a product’s stock.

| Parameter | Description           | Required | Type  |
|-----------|-----------------------|----------|--------|
| `id`      | UUID of the product   | Yes      | UUID   |
| `stock`   | New stock value       | Yes      | int    |


#### Responses

| Code   | Description           | Content                   |
|--------|-----------------------|---------------------------|
| `200`  | Stock updated         | Empty                     |
| `404`  | Product not found     | `application/json`: Error |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### `DELETE /products/{id}`

#### Description
Deletes a product by ID.

| Parameter | Description         | Required | Type  |
|-----------|---------------------|----------|--------|
| `id`      | UUID of the product | Yes      | UUID   |

#### Responses

| Code   | Description           | Content                   |
|--------|-----------------------|---------------------------|
| `204`  | Product deleted       | No Content                |
| `404`  | Product not found     | `application/json`: Error |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### *Orders*
### `POST /orders`

#### Description
Creates a new order.

#### Body
```json
{
  "buyerEmail": "client@email.com",
  "seatLetter": "A",
  "seatNumber": 12
}
```

#### Responses

| Code   | Description         | Content                 |
|--------|---------------------|-------------------------|
| `200`  | Order created       | `application/json`: Order |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### `GET /orders/{orderId}`

| Parameter | Description      | Required | Type  |
|-----------|------------------|----------|--------|
| `orderId` | UUID of the order| Yes      | UUID   |

#### Responses

| Code   | Description       | Content                 |
|--------|-------------------|-------------------------|
| `200`  | Order found       | `application/json`: Order |
| `404`  | Order not found   | `application/json`: Error |

---

### `PUT /orders/{orderId}`

#### Body
```json
{
  "buyerEmail": "client@email.com",
  "productIds": ["id1", "id2"]
}
```

#### Responses

| Code   | Description         | Content                 |
|--------|---------------------|-------------------------|
| `200`  | Order updated       | `application/json`: Order |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |

---

### `POST /orders/{orderId}/finish`

#### Body
```json
{
  "cardToken": "abc123",
  "paymentGateway": "STRIPE"
}
```

#### Responses

| Code   | Description         | Content                 |
|--------|---------------------|-------------------------|
| `200`  | Order finished      | `application/json`: Order |

---

### `POST /orders/{orderId}/cancel`

#### Responses

| Code   | Description         | Content                 |
|--------|---------------------|-------------------------|
| `200`  | Order canceled      | `application/json`: Order |
| `401` | The request is unauthenticated| `application/json`: Unauthorized         |
| `500` | Unexpected error             | `application/json`: Internal server error |


### Postman Collection

To make testing easier, you can import the following collection into Postman:
[Download Postman Collection](docs/postman/OrderServiceCollection.postman_collection.json)


## Technology and Architecture Justification

### **Java 17**
- **LTS (Long-Term Support)**: Ensures long-term updates and security patches.
- **Performance improvements**: Optimized garbage collection and JIT efficiency.
- **Compatibility**: Smooth migration from older versions without compromising stability.

### **Spring Boot**
- **Rapid development**: Auto-configuration accelerates development.
- **Microservices ready**: Great for scalable and maintainable services.
- **Ecosystem integration**: Comes with Spring Data, Spring Security, Spring Cloud, etc.
- **Minimal configuration**: Reduces boilerplate setup.

### Why H2 In-Memory Database
- **Lightweight**: Ideal for development/testing environments.
- **No external dependencies**: Starts quickly without setup.
- **Fast**: Operates entirely in memory for speed.
- **PostgreSQL compatibility**: Simulates production environments closely.

### Why Spring Data JPA
- **Database abstraction**: Interact with DB using object-oriented models.
- **Built-in repository features**: Reduce boilerplate CRUD code.
- **Automatic handling**: Manages transactions and persistence context seamlessly.
- **Spring ecosystem compatibility**: Works natively with Spring Boot.

### Repository Pattern Usage
- **Separation of concerns**: Isolates data access from business logic.
- **Maintainability**: One place to change DB logic.
- **Testability**: Easier to mock or stub during tests.

Repositories act as the access point for database entities like **orders**, **products**, and **categories**, keeping business logic clean and decoupled.

---