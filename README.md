# Learning Management System (LMS)

This is a Spring Boot-based Learning Management System (LMS) that manages users, courses, and roles such as Admin, Instructor, and Student. The system is backed by a PostgreSQL database and uses Spring Security for authentication and authorization.

---

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
  - [Prerequisites](#prerequisites)
  - [Clone the Repository](#clone-the-repository)
  - [Set Up the Database](#set-up-the-database)
  - [Run the Application](#run-the-application)

---

## Features

1. **User Management**
   - Role-based access control (Admin, Instructor, Student).
   - User registration and login.

2. **Course Management**
   - Admins can manage courses and users.
   - Instructors can create and manage courses.
   - Students can enroll in courses and view content.

3. **Security**
   - Password encryption using `BCrypt`.
   - Authentication and Authorization via Spring Security.

4. **Database**
   - PostgreSQL for data persistence.

---

## Technologies Used

- **Java 23**
- **Spring Boot 3.4**
- **Spring Security**
- **PostgreSQL**

---

## Setup Instructions

### Prerequisites

1. Install Java 23 or higher.
2. Install PostgreSQL (version 14 or higher).
3. Install Maven.

---

### Clone the Repository

```bash
# Clone the repository
git clone https://github.com/<your-username>/<your-repo>.git
cd <your-repo>
```

---

### Set Up the Database

1. Create a database in PostgreSQL named `lms`.
   ```sql
   CREATE DATABASE lms;
   ```
2. Update the database credentials in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/lms
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. Run the following SQL to manually add a user (optional):
   ```sql
   INSERT INTO users (username, password, first_name, last_name, email, role) VALUES (
       'admin',
       '$2a$10$KixlF4YtZT0C03V6dYfzXeODKbsLg3Z6bQZ.JL5WEdwNi2lF6n5Jq', -- "password123" hashed
       'System',
       'Admin',
       'admin@example.com',
       'ADMIN'
   );
   ```

---

### Run the Application

1. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
2. Access the application at [http://localhost:8080](http://localhost:8080).
