# <h1 align="center">📚 Online Book Store 📚</h1>
#### <h4 align="center"> `The best application to order books!` </h4>

### 👋 Introduction
`This project is a Spring Boot implementation of an Online Book Store. It encompasses several domain models, including User, Role, Book, Category, ShoppingCart, CartItem, Order, and OrderItem. Users have the ability to register, sign in, browse books, add them to their shopping cart, and place orders. Administrators, on the other hand, can manage books, bookshelf sections, as well as view and update order statuses.`

### 👩‍💻 Technologies Used
`The following technologies are used to build the Booking application:`
- ☕ **Java**: The primary programming language used for the application.
- 🌱 **Spring Boot**: A powerful framework that provides essential features for building web applications.
- 🌱🛢️ **Spring Data JPA**: Simplifies data access and persistence with JPA (Java Persistence API).
- 🌱🛡️ **Spring Security**: Enables robust and secure authentication and authorization mechanisms.
- 🗎 **Swagger**: Provides API documentation.
- 🐬 **MySQL**: The database management system used for data storage.
- 🌶️ **Lombok**: Reduces boilerplate code with annotations.
- ↔️ **MapStruct**: Simplifies object mapping between DTOs and entities.

### ❓ How to use
`Before running the Booking app, ensure you have the following installed:`
- ☕ Java Development Kit (JDK)

`Follow the steps below to install:`
1. Clone the repository from GitHub and navigate to the project directory.
2. Fill up application.properties and liquibase.properties files with the necessary environment variables.
3The application should now be running at `http://localhost:8080`.

### 👷 Project architecture:
![architecture.png](assets%2Farchitecture.png)

### 🛢️ Database structure:
#### <h4 align="center">![DB_Plan.png](assets/DB_plan.png) </h4>
###  API Endpoints
`Online Book Store provides the following API endpoints:`

| **HTTP method** | **Endpoint**                 | **Role**    | **Function**                                           |
|:----------------|:-----------------------------|-------------|:-------------------------------------------------------|
| POST            | /auth/register               | ALL         | Register a new user.                                   |
| POST            | /auth/login                  | ALL         | Get JWT token for authentication.                      |
| POST            | /books                       | ADMIN       | Add new book.                                          |
| GET             | /books                       | ADMIN/USER  | Get a list of all books.                               |
| GET             | /books/{id}                  | ADMIN/USER  | Get detailed information about book.                   |
| PUT             | /books/{id}                  | ADMIN       | Update book information.                               |
| DELETE          | /books/{id}                  | ADMIN       | Delete book.                                           |
| POST            | /orders                      | ADMIN/USER  | Create order from user's shopping cart content         |
| GET             | /orders                      | ADMIN/USER  | Get orders history                                     |
| GET             | /orders/{orderId}/items      | ADMIN/USER  | Get user's orders items list                           |
| GET             | /orders/{orderId}/items/{id} | ADMIN/USER  | Responses user's specific item from orders items by id |
| PATCH           | /orders/{id}                 | ADMIN       | Updates order's status by id                           |
| POST            | /categories                  | ADMIN       | Add a new category.                                    |
| GET             | /categories                  | ADMIN/USER  | Get a range of all books categories                    |
| GET             | /categories/{id}             | ADMIN/USER  | Get specific category by ID.                           |
| GET             | /categories/{id}/books       | ADMIN/USER  | Get a list of books with specific category             |
| DELETE          | /categories/{id}             | ADMIN       | Deletes a specific category                            |
| PUT             | /categories/{id}             | ADMIN       | Update information about specific category             |
| GET             | /cart                        | ADMIN/USER  | Get all information about shopping cart content        |
| DELETE          | /cart/cart-items/{id}        | ADMIN/USER  | Delete a cart item from a shopping cart                |
| POST            | /cart                        | ADMIN/USER  | Add cart item                                          |
| PUT             | /cart/{id}                   | ADMIN/USER  | Update cart item quantity                              |

### 🤝 Contribution Guidelines
`We welcome contributions to this project:`

For every new feature or bug fix, please establish a separate branch and initiate a pull request to the primary branch. Prior to merging, it is imperative that all pull requests undergo thorough review and receive approval from at least one team member.