Weather API
A Spring Boot REST API to fetch weather information for a given pincode using OpenWeather API. This project demonstrates API design, persistence with H2 database, and unit testing with JUnit.

Features
Get weather information for a pincode and date.

Fetch data from OpenWeather API and store in local DB.

H2 in-memory database for quick testing.

Unit tests using JUnit and Mockito.

Getting Started
Prerequisites
- Java 17+ (Project tested on Java 24)

Maven 3.8+

Internet access (for API calls)

Setup
Clone this repo:

bash
Copy
Edit
git clone https://github.com/ghratika/weather-api.git
cd weather-api
Configure your OpenWeather API Key in src/main/resources/application.properties:

properties
Copy
Edit
openweather.api.key=your_api_key_here
Run the app:

bash
Copy
Edit
mvn spring-boot:run
Access API at:

bash
Copy
Edit
http://localhost:8080/api/weather
API Endpoints
Method	Endpoint	Description	Parameters
GET	/api/weather	Get weather for pincode & date	pin (required), forDate (optional, yyyy-MM-dd)
GET	/api/weather/all	Get all cached weather data	None

Example:

bash
Copy
Edit
GET /api/weather?pin=110001&forDate=2025-07-07
Database
Tables
PINCODE_INFO: Stores pincode with latitude & longitude.

WEATHER_INFO: Stores weather data per pincode & date.

Access H2 Console:

bash
Copy
Edit
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:weatherdb

Tests
Run tests using:

bash
Copy
Edit
mvn test
Includes:

Controller tests

Service layer tests

Repository tests

Technologies Used
Spring Boot

Spring Data JPA

H2 Database

Maven

JUnit 5 and Mockito

OpenWeather API

