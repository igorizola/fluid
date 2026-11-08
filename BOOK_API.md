# Book Management API

This Spring Boot application provides a complete CRUD API for managing books using Spring Data JDBC and H2 database.

## Project Structure

```
src/main/java/no/example/fluid/
├── controller/
│   └── BookController.java      # REST API endpoints
├── service/
│   └── BookService.java          # Business logic
├── repository/
│   └── BookRepository.java       # Data access layer
├── entity/
│   └── Book.java                 # Domain model
└── FluidApplication.java         # Main application class
```

## Database Configuration

- **Database**: H2 (In-memory)
- **URL**: `jdbc:h2:mem:testdb`
- **Console**: Available at `http://localhost:8080/h2-console`
- **Username**: sa
- **Password**: (empty)

### Database Schema

```sql
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    price DOUBLE,
    year_published INT
);
```

## API Endpoints

### Get All Books
```
GET /api/books
Response: 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "978-0132350884",
    "price": 35.99,
    "year": 2008
  },
  ...
]
```

### Get Book by ID
```
GET /api/books/{id}
Response: 200 OK or 404 Not Found
Content-Type: application/json

{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "price": 35.99,
  "year": 2008
}
```

### Create Book
```
POST /api/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "price": 35.99,
  "year": 2008
}

Response: 201 Created
```

### Update Book
```
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Clean Code (2nd Edition)",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "price": 39.99,
  "year": 2024
}

Response: 200 OK or 404 Not Found
```

### Delete Book
```
DELETE /api/books/{id}
Response: 204 No Content or 404 Not Found
```

## Running the Application

### Build
```bash
./mvnw clean compile
```

### Test
```bash
./mvnw test
```

### Run
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## Technology Stack

- **Spring Boot 4.2.0-SNAPSHOT**
- **Spring Data JDBC** - For database access
- **Spring Web** - For REST API
- **H2 Database** - Embedded database
- **Java 25**
- **Maven** - Build tool

## Key Components

### Book Entity (`no.example.fluid.entity.Book`)
- Domain model representing a book
- Uses Spring Data JDBC annotations
- Fields: id, title, author, isbn, price, year

### BookRepository (`no.example.fluid.repository.BookRepository`)
- Extends `CrudRepository<Book, Long>`
- Provides basic CRUD operations
- No custom queries needed for this example

### BookService (`no.example.fluid.service.BookService`)
- Service layer with business logic
- Handles all book operations
- Provides exception handling
- Methods: getAllBooks, getBookById, createBook, updateBook, deleteBook

### BookController (`no.example.fluid.controller.BookController`)
- REST controller with `/api/books` endpoints
- Handles HTTP requests and responses
- Returns appropriate HTTP status codes
- Input validation at controller level
