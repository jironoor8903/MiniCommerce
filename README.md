# MiniCommerce
**Author: Jiro Noor**
**Link:** https://github.com/jironoor8903/MiniCommerce
**Demo:** [https://youtu.be/oqKd2rExlA8](https://youtu.be/f-_B8QSx66U)

## Project Overview

This project involves the development of a basic e-commerce platform API using Spring Boot, JPA (Java Persistence API), and PostgreSQL. The platform allows users to sign up, sign in, buy, and sell products. It manages user accounts, product listings, and user transactions.

## System Architecture

The system follows a layered architecture pattern:

1. **Controller Layer:** Handles HTTP requests and responses, mapping them to the appropriate services.
2. **Service Layer:** Contains the business logic, interacting with the repository layer to fetch, update, or persist data.
3. **Repository Layer:** Manages CRUD operations with the database through JPA repositories.
4. **Entity Layer:** Defines the data model, representing tables in the database.
![](https://paper-attachments.dropboxusercontent.com/s_76947782B9C969847701CAA96C951F312F0FF8F4DE2196005DD6AF183E8BC00B_1724976428367_Screenshot+2024-08-29+at+5.07.04PM.png)



## Database Design

The database schema involves two primary tables: `User` and `Product`.
**User Table**

- **Columns:**
    - `id`: Primary key, auto-generated.
    - `username`: Unique, identifies the user.
    - `passwordHash`: Stores the hashed password.
    - `email`: User's email, unique.
    - `storeCredit`: Amount of store credit the user has.
    - `balance`: Monetary balance of the user.
- **Relationships:**
    - `One-to-Many` with `Product`: A user can sell or buy multiple products.

**Product Table**

- **Columns:**
    - `id`: Primary key, auto-generated.
    - `name`: Name of the product.
    - `category`: Category to which the product belongs.
    - `description`: Description of the product.
    - `originalPrice`: The original price of the product.
    - `resellPrice`: The price at which the product is being resold.
    - `soldByUser`: Foreign key referencing the `User` who sold the product.
    - `boughtByUser`: Foreign key referencing the `User` who bought the product.
- **Relationships:**
    - `Many-to-One` with `User`: Tracks which user sold and bought each product.


![](https://paper-attachments.dropboxusercontent.com/s_76947782B9C969847701CAA96C951F312F0FF8F4DE2196005DD6AF183E8BC00B_1724989828165_Screenshot+2024-08-29+at+8.50.23PM.png)

# Product Module
## 1. Product Entity
- **Attributes:**
    - `id`: Auto-generated primary key.
    - `name`: Name of the product.
    - `category`: Category under which the product is listed.
    - `description`: A brief description of the product.
    - `originalPrice`: The initial price of the product.
    - `resellPrice`: The price at which the product is being resold.
    - `soldByUser`: The user who is selling the product (foreign key to `User` entity).
    - `boughtByUser`: The user who has bought the product (foreign key to `User` entity).
- **Relationships:**
    - `Many-to-One` with `User`: A product can be sold or bought by one user. The `soldByUser` and `boughtByUser` fields link the product to the users involved in its transaction.
    - 
## 2. ProductRequest
- **Purpose:** The `ProductRequest` class is a Data Transfer Object (DTO) used to encapsulate the data required to create a new product. 
- **Attributes:**
    - `name`: Name of the product.
    - `category`: Category under which the product is listed.
    - `description`: A brief description of the product.
    - `originalPrice`: The initial price of the product.
    - `resellPrice`: The price at which the product is being resold.
    
## 3. ProductRepository
- **Purpose:** The `ProductRepository` is an interface extending `JpaRepository`, providing CRUD operations and custom query methods.
- **Key Methods:**
    - `findByCategory(String category, Pageable pageable)`: Fetches products by category with pagination support.
    - `findByName(String name, Pageable pageable)`: Fetches products by name with pagination support.
    - `findByCategoryAndName(String category, String name, Pageable pageable)`: Fetches products by both category and name with pagination support.
    - `findByResellPrice(double resellPrice)`: Fetches products by their resell price.
    - `findByResellPriceBetween(double minPrice, double maxPrice)`: Fetches products within a specific price range.
## 4. ProductService
- **Purpose:** The `ProductService` class contains the business logic for managing products. It interacts with the `ProductRepository` to retrieve, add, and filter products.
- **Key Methods:**
    - `getProducts(String category, String name, int page, int size)`: Retrieves products based on category, name, and pagination.
    - `getProductById(Long id)`: Retrieves a product by its ID.
    - `addProduct(Product product)`: Adds a new product to the database.
    - `getProductsByResellPrice(double resellPrice)`: Retrieves products by their resell price.
    - `getProductsByPriceRange(double minPrice, double maxPrice)`: Retrieves products within a specified price range.
## 5. ProductController
- **Purpose:** The `ProductController` class is a REST controller that exposes endpoints for product-related operations. It interacts with the `ProductService` to process requests and return responses.
    **1. GET /api/v1/product**
    - **Query Parameters:**
        - **category** (`String`, optional): Filter by product category. Example: `/api/v1/product?category=Shoes`.
        - **name** (`String`, optional): Filter by product name. Example: `/api/v1/product?name=Air+Jordan`.
        - **page** (`int`, default: `0`): Page number for pagination. Example: `/api/v1/product?page=1`.
        - **size** (`int`, default: `10`): Number of products per page. Example: `/api/v1/product?size=20`.
    **2. GET /api/v1/product/{id}**
    - **Path Variable:**
        - **id** (`Long`, required): Unique ID of the product to retrieve. Example: `/api/v1/product/3`
    **3. POST /api/v1/product**
    - **Request Body Parameters:**
        - **name** (`String`, required): Product name.
        - **category** (`String`, required): Product category.
        - **description** (`String`, required): Product description.
        - **originalPrice** (`double`, required): Original price.
        - **resellPrice** (`double`, required): Resell price.
    **4. GET /api/v1/product/by-price**
    - **Query Parameter:**
        - **price** (`double`, required): Exact resell price to filter by. Example: `/api/v1/product/by-price?price=50.75`.
    **5. GET /api/v1/product/by-price-range**
    - **Query Parameters:**
        - **minPrice** (`double`, required): Minimum resell price.
        - **maxPrice** (`double`, required): Maximum resell price.
        - Example: `/api/v1/product/by-price-range?minPrice=50.00&maxPrice=100.00`.
# User Module
## 1. User Entity

The `User` entity represents a user in the system. It is mapped to the `app_user` table in the database.
**Key Fields:**

- `id`: Unique identifier for each user.
- `username`: The username of the user.
- `passwordHash`: Hashed password for authentication.
- `email`: Email address of the user.
- `storeCredit`: Credit that the user can use to purchase products.
- `balance`: The cash balance of the user.
- `productsBought`: A list of products the user has purchased.

**Relationships:**

- One-to-Many with `Product`: The `User` entity has a one-to-many relationship with the `Product` entity for both the products they have bought and sold.
## 2. User Controller

The `UserController` class provides the RESTful API endpoints for managing user-related operations. Each endpoint corresponds to specific user actions, such as registration, login, balance management, and product transactions.
**Key Endpoints and Example Request Bodies:**

- `**GET /api/v1/user**`:
    - **Purpose**: Retrieves a list of all users in the system.
    - **Implementation**: Invokes the `getUsers()` method in `UserService`, which fetches all users from the database.
- `**GET /api/v1/user/{id}**`:
    - **Purpose**: Fetches a specific user by their unique ID.
    - **Implementation**: Calls `getUserById(Long id)` in `UserService`, which returns the user entity if found, or a `404 NOT FOUND` status if the user doesn’t exist.
- `**GET /api/v1/user/by-username**`:
    - **Purpose**: Retrieves a user by their username.
    - **Implementation**: Calls `getUserByUsername(String username)` in `UserService`, returning the user entity if found, or `404 NOT FOUND` otherwise.
    - **Query Parameter**: `username` (String)
- `**GET /api/v1/user/by-email**`:
    - **Purpose**: Fetches a user by their email address.
    - **Implementation**: Invokes `getUserByEmail(String email)` in `UserService`, returning the user entity if found, or `404 NOT FOUND` otherwise.
    - **Query Parameter**: `email` (String)
- `**POST /api/v1/user/sign-up**`:
    - **Purpose**: Registers a new user in the system.
    - **Implementation**: Calls the `signUpUser(User user)` method in `UserService` with the data provided in the request body. Returns `201 CREATED` on success or `400 BAD REQUEST` if the username or email is already taken.
    - **Request Body**:
    {
      "username": "jiroNoor",
      "email": "jiro@example.com",
      "password": "securePassword123"
    }
- `**POST /api/v1/user/sign-in**`:
    - **Purpose**: Authenticates a user based on their credentials.
    - **Implementation**: The `signIn` method in `UserController` validates the provided username and password. If successful, it returns a success message; otherwise, it returns `401 UNAUTHORIZED`.
    - **Request Body**:
    {
      "username": "jiroNoor",
      "password": "securePassword123"
    }
- `**POST /api/v1/user/add-to-balance**`:
    - **Purpose**: Adds funds to a user’s balance.
    - **Implementation**: Calls `addToBalance(AddBalanceRequest request)` in `UserService`, which updates the user's balance if the credentials are valid.
    - **Request Body**:
    {
      "username": "jiroNoor",
      "password": "securePassword123",
      "amount": 50.00
    }
- `**POST /api/v1/user/buy-product**`:
    - **Purpose**: Facilitates the purchase of a product by the user.
    - **Implementation**: The `buyProduct(BuyProductRequest request)` method in `UserService` is invoked to handle the purchase transaction. The endpoint returns the purchased product or an error if the transaction fails.
    - **Request Body**:
    {
      "username": "jiroNoor",
      "password": "securePassword123",
      "productId": 1
    }
- **`POST /api/v1/user/sell-product`**:
    - **Purpose**: Allows a user to sell a product.
    - **Implementation**: The `sellProduct(SellProductRequest request)` method in `UserService` handles the creation of a new product entry and updates the user's store credit accordingly.
    - **Request Body**:
    - `json { "username": "jiroNoor", "password": "securePassword123", "productName": "Air Jordan 1", "productCategory": "Shoes", "productDescription": "Nike Basketball Shoe", "originalPrice": 150.00, "resellPrice": 120.00 }`
## 3. User Service

The `UserService` class is the core business logic handler for user-related operations. It acts as an intermediary between the controllers and repositories, ensuring that all user-related processes are executed properly. The `UserService` manages user registration, authentication, balance transactions, and product-related operations, such as buying and selling.
**Key Methods:**

- `**signUpUser(User user)**`:
    - **Purpose**: Handles the registration of a new user.
    - **Details**:
        - It first checks whether the username or email provided by the new user already exists in the database. This is done by querying the `UserRepository` using custom methods `findByUsername` and `findByEmail`.
        - If either the username or email already exists, the method throws a `RuntimeException` to prevent duplicate accounts.
        - If the username and email are unique, the password is hashed using `BCryptPasswordEncoder` for secure storage.
        - The method initializes the user’s balance and store credit to `0.0` and then saves the new user entity to the database using `userRepository.save(user)`.
- `**updateUser(Long id, User newUserDetails)**`:
    - **Purpose**: Updates the details of an existing user.
    - **Details**:
        - The method retrieves the user by their ID from the `UserRepository`.
        - If the user exists, their details are updated with the new information provided in the `newUserDetails` object.
        - Fields such as `username`, `passwordHash`, `email`, `storeCredit`, and `balance` are updated.
        - The updated user entity is then saved back to the database.
        - If the user with the provided ID does not exist, the method throws a `RuntimeException`.
- `**addToBalance(AddBalanceRequest request)**`:
    - **Purpose**: Adds a specified amount to the user’s account balance.
    - **Details**:
        - The method first retrieves the user by their username using `findByUsername`.
        - It then validates the user’s password by comparing the provided raw password with the stored hashed password using `passwordEncoder.matches`.
        - If the password is correct, the method adds the specified amount to the user’s current balance.
        - The updated user entity is saved back to the database.
        - If the user does not exist or the password is incorrect, an appropriate `RuntimeException` is thrown.
- `**buyProduct(BuyProductRequest request)**`:
    - **Purpose**: Facilitates the purchase of a product by a user.
    - **Details**:
        - The method retrieves the user based on their username and validates their password.
        - It then fetches the product to be purchased using the `productRepository.findById`.
        - If the product has already been bought by another user, a `RuntimeException` is thrown to prevent double purchasing.
        - The method checks if the user has sufficient funds (balance and store credit combined) to cover the product’s resell price.
        - The purchase amount is first deducted from the user’s store credit, and if necessary, the remainder is deducted from their balance.
        - The product’s `boughtByUser` field is set to the current user, and the updated product is saved.
        - If the product is unavailable, the funds are insufficient, or the password is incorrect, appropriate exceptions are raised.
- `**sellProduct(SellProductRequest request)**`:
    - **Purpose**: Handles the process of selling a product by a user.
    - **Details**:
        - The method retrieves the user based on their username and validates their password.
        - A new `Product` entity is created using the details provided in the `SellProductRequest`.
        - The `soldByUser` field of the product is set to the current user, while the `boughtByUser` field is initially set to `null`.
        - The product is saved in the database via the `productRepository`.
        - The user’s store credit is increased by the product’s resell price, reflecting the proceeds from the sale.
        - If the user does not exist or the password is incorrect, a `RuntimeException` is thrown.
## 4. User Repository

The `UserRepository` interface provides data access operations for the `User` entity. It extends `JpaRepository`, which automatically provides basic CRUD operations (Create, Read, Update, Delete) and allows the definition of custom query methods to retrieve user data based on specific attributes.
**Key Methods:**

- `**findByUsername(String username)**`:
    - **Purpose**: Fetches a user by their username.
    - **Implementation**: This custom query method returns an `Optional<User>`. It is primarily used during user authentication and registration to check for the existence of a username in the database.
    - **Usage Example**: Used when a new user tries to sign up to ensure the username is unique.
- `**findByEmail(String email)**`:
    - **Purpose**: Retrieves a user by their email address.
    - **Implementation**: Similar to `findByUsername`, this method returns an `Optional<User>` and is used to check the uniqueness of an email during user registration.
    - **Usage Example**: Utilized during sign-up to ensure the email provided is not already registered.

