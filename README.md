# Mail_System (Spring Boot)

This is a mock implementation of a resilient email-sending service, designed as part of a coding assessment or technical interview. The system is built using Spring Boot and demonstrates several production-grade concerns such as retry logic, fallback between providers, rate limiting, idempotency, and status tracking.

---

## Features

- Retry mechanism with exponential backoff
- Fallback between two mock email providers
- Idempotency to prevent duplicate sends using a client-supplied key
- Basic per-recipient rate limiting
- In-memory status tracking
- REST API for sending emails and querying their status
- Clean and testable service design using SOLID principles
- Mocked email providers (no real email delivery)

---

## Technologies Used

- Java 17
- Spring Boot 3.x
- Maven
- JUnit 5 (for unit testing)
- SLF4J with Logback (for logging)

---

## Project Structure


## Setup Instructions

1 Clone the repository
 
git clone https://github.com/Akshat-Rastogi-007/Mail_System
cd Mail_System

2. Build the project
   ./mvnw clean install
   
4. Run the project
   ./mvnw spring-boot:run
   
The application will start at: https://mail-system-09c4.onrender.com/

**API Endpoints**

1. Send Email
POST /api/email/send

Request Body:

{
  "idempotencyKey": "abc123",
  "to": "user@example.com",
  "subject": "Test Subject",
  "body": "Sample email body"
}

**Responses**:

200 OK – Email sent successfully

429 Too Many Requests – Rate limit exceeded

500 Internal Server Error – All providers failed after retries

2. Get Email Status

GET /api/email/status?idempotencyKey=abc123

Responses:
Email status: SUCCESS
Email status: RETRYING
Email status: FAILED
Email status: RATE_LIMITED

**Assumptions**

Email providers are mocked (no actual emails are sent).

Idempotency is implemented using a client-supplied unique key.

Rate limiting is applied per recipient email address (1 request per 5 seconds).

Status tracking is stored in-memory and is reset on application restart.

Retry logic uses exponential backoff: 1s, 2s, 4s per provider (up to 3 attempts).

Providers are attempted in a fixed order (fallback logic).

**Logging**

Logging is implemented using SLF4J and Logback.

Logs can be configured to write to console and rolling files via logback-spring.xml.

Author
Created by Akshat Rastogi.
contact akshatrastogi.jaipuriahardoi@gmail.com

   




