# JobTracker — Application Service

Manages job applications for the JobTracker microservices platform.

## Responsibilities
- Create and track job applications
- Status lifecycle management
- Application statistics and dashboard data

## Tech Stack
- Java 21 / Spring Boot 3.x
- Spring Data JPA + PostgreSQL + Flyway
- JWT validation (shared secret with user-service)
- Spring Cloud OpenFeign
- Lombok

## Application Status Flow
APPLIED → INTERVIEW_SCHEDULED → INTERVIEWED → OFFER_RECEIVED → ACCEPTED

→ REJECTED

→ WITHDRAWN

## Endpoints
| Method | Path | Description |
|--------|------|-------------|
| POST | /api/applications | Create a new application |
| GET | /api/applications | Get all my applications (paginated) |
| GET | /api/applications/{id} | Get single application |
| PUT | /api/applications/{id} | Update application |
| PATCH | /api/applications/{id}/status | Update status only |
| DELETE | /api/applications/{id} | Delete application |
| GET | /api/applications/stats | Get dashboard statistics |

All endpoints require a valid JWT token in the `Authorization: Bearer <token>` header.

## Running Locally

### Prerequisites
- Java 21+
- PostgreSQL running
- Create a database named `jobtracker_applications`
- `user-service` running on port 8081

### Setup
1. Clone the repo
2. Create a `.env` file in the root:
```env
DATASOURCE_URL=jdbc:postgresql://localhost:5432/jobtracker_applications
DATASOURCE_USERNAME=your_username
DATASOURCE_PASSWORD=your_password
JWT_SECRET=your-secret-key-minimum-32-characters
```
3. Run the service:
```bash
./mvnw spring-boot:run
```

The service starts on **port 8082**.

## Running Tests
```bash
./mvnw test
```

## Environment Variables
| Variable | Description |
|----------|-------------|
| DATASOURCE_URL | PostgreSQL connection URL |
| DATASOURCE_USERNAME | Database username |
| DATASOURCE_PASSWORD | Database password |
| JWT_SECRET | Must match user-service JWT secret |

## Example Request
```json
POST /api/applications
Authorization: Bearer <token>

{
  "companyName": "Google",
  "jobTitle": "Backend Engineer",
  "jobUrl": "https://careers.google.com/jobs/123",
  "salaryExpectation": 150000,
  "appliedDate": "2026-06-15",
  "deadline": "2026-07-01",
  "notes": "Applied through referral"
}
```
