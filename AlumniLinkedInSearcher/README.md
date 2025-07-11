
# Alumni LinkedIn Profile Searcher

This is a Spring Boot backend application for searching and storing LinkedIn profiles of alumni from a specified university using the PhantomBuster API.

##  Features

- Search alumni by university, designation, and optional pass-out year.
- Save alumni data in PostgreSQL database.
- Retrieve all saved alumni profiles.

##  Tech Stack

- Java 15
- Spring Boot
- PostgreSQL
- Maven
- PhantomBuster API

## ⚙️ Setup Instructions

### 1️ Prerequisites

- Java 15 installed
- PostgreSQL installed
- Maven installed

### 2️ Database Setup

1. Start PostgreSQL and create a database:
   ```sql
   CREATE DATABASE alumni_db;
   ```

2. Update `src/main/resources/application.properties` with your DB credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/alumni_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   phantomBuster.api.key=fJk7Xg4wOvdYtACj8pLuaWoqHaHrDzWbuLiVBYipOQc
   ```

### 3️⃣ Run the Application

In the project root directory:
```bash
./mvnw spring-boot:run
```

## 📬 API Endpoints

### POST `/api/alumni/search`

#### Request Body:
```json
{
  "university": "University of XYZ",
  "designation": "Software Engineer",
  "passoutYear": 2020
}
```

#### Response Example:
```json
{
  "status": "success",
  "data": [
    {
      "name": "John Doe",
      "currentRole": "Software Engineer",
      "university": "University of XYZ",
      "location": "New York, NY",
      "linkedinHeadline": "Passionate Software Engineer at XYZ Corp",
      "passoutYear": 2020
    }
  ]
}
```

### GET `/api/alumni/all`

#### Response Example:
```json
{
  "status": "success",
  "data": [
    {
      "name": "John Doe",
      "currentRole": "Software Engineer",
      "university": "University of XYZ",
      "location": "New York, NY",
      "linkedinHeadline": "Passionate Software Engineer at XYZ Corp",
      "passoutYear": 2020
    }
  ]
}
```

##  Testing

Run JUnit tests with:
```bash
./mvnw test
```

##  Notes

- PhantomBuster API is required for fetching alumni data.
- Only backend APIs are implemented (no UI).
