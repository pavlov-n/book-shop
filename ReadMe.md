# <h1 align="center">📚 Online Book Store 📚</h1>
#### <h4 align="center"> `The best application to order books!` </h4>

### 👋 Introduction
`This project is a Spring Boot implementation of an Online Book Store. It encompasses several domain models, including User, Role, Book, Category, ShoppingCart, CartItem, Order, and OrderItem. Users have the ability to register, sign in, browse books, add them to their shopping cart, and place orders. Administrators, on the other hand, can manage books, bookshelf sections, as well as view and update order statuses.`

### 👩‍💻 Technologies Used
`The following technologies are used to build the Booking application:`
- ☕ **Java 17**: The primary programming language used for the application.
- 🌱 **Spring Boot v3.1.4**: A powerful framework that provides essential features for building web applications.
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
3. The application should now be running at `http://localhost:8080`.

### 👷 Project architecture:
![architecture.png](assets%2Farchitecture.png)

### 🛢️ Database structure:
#### <h4 align="center">![DB_Plan.png](assets%2FDB_Plan.png) </h4>
###  API Endpoints
`Online Book Store provides the following API endpoints:`


#### 🔒Authorization endpoints

| **HTTP method** | **Endpoint**    | **Role**    | **Function**                       |
|:----------------|:----------------|-------------|:-----------------------------------|
| POST            | /auth/register  | ALL         | Register a new user.               |
| POST            | /auth/login     | ALL         | Get JWT token for authentication.  |

#### 📘Books management endpoints

| **HTTP method** | **Endpoint** | **Role**    | **Function**                          |
|:----------------|:-------------|-------------|:--------------------------------------|
| POST            | /books       | ADMIN       | Add new book.                         |
| GET             | /books       | ADMIN/USER  | Get a list of all books.              |
| GET             | /books/{id}  | ADMIN/USER  | Get detailed information about book.  |
| PUT             | /books/{id}  | ADMIN       | Update book information.              |
| DELETE          | /books/{id}  | ADMIN       | Delete book.                          |

#### 📝Orders management endpoints
| **HTTP method** | **Endpoint**                 | **Role**    | **Function**                                           |
|:----------------|:-----------------------------|-------------|:-------------------------------------------------------|
| POST            | /orders                      | ADMIN/USER  | Create order from user's shopping cart content         |
| GET             | /orders                      | ADMIN/USER  | Get orders history                                     |
| GET             | /orders/{orderId}/items      | ADMIN/USER  | Get user's orders items list                           |
| GET             | /orders/{orderId}/items/{id} | ADMIN/USER  | Responses user's specific item from orders items by id |
| PATCH           | /orders/{id}                 | ADMIN       | Updates order's status by id                           |

#### 🔖Categories management endpoints
| **HTTP method** | **Endpoint**            | **Role**    | **Function**                                |
|:----------------|:------------------------|-------------|:--------------------------------------------|
| POST            | /categories             | ADMIN       | Add a new category.                         |
| GET             | /categories             | ADMIN/USER  | Get a range of all books categories         |
| GET             | /categories/{id}        | ADMIN/USER  | Get specific category by ID.                |
| GET             | /categories/{id}/books  | ADMIN/USER  | Get a list of books with specific category  |
| DELETE          | /categories/{id}        | ADMIN       | Deletes a specific category                 |
| PUT             | /categories/{id}        | ADMIN       | Update information about specific category  |

#### 🛒Shopping-carts management endpoints
| **HTTP method** | **Endpoint**          | **Role**    | **Function**                                     |
|:----------------|:----------------------|-------------|:-------------------------------------------------|
| GET             | /cart                 | ADMIN/USER  | Get all information about shopping cart content  |
| DELETE          | /cart/cart-items/{id} | ADMIN/USER  | Delete a cart item from a shopping cart          |
| POST            | /cart                 | ADMIN/USER  | Add cart item                                    |
| PUT             | /cart/{id}            | ADMIN/USER  | Update cart item quantity                        |

### 🤝 Contact
`We welcome contributions to this project:`

If you want to add something new, please contact me at this email: pavlovmykyta1@gmail.com 