# Auth API 🔐

REST API built with Spring Boot that implements JWT authentication and role-based access control.

## Tech Stack

Java 21 · Spring Boot 3.x · Maven · Docker · Spring Security · jjwt

## Getting Started

### Option 1: Docker (recommended) 🐳

Build the image:
```bash
docker build -t auth-api .
```

Run the container:
```bash
docker run -p 8080:8080 \
  -e JWT_SECRET=your-secret-key-min-32-characters \
  -e ADMIN_MAIL=admin@example.com \
  -e ADMIN_PASSWORD=your-admin-password \
  auth-api
```

### Option 2: Local 💻

Requirements: Java 21, Maven

1. Clone the repository
2. Set the required environment variables (see Configuration)
3. Run the application:
```bash
./mvnw spring-boot:run
```

## Configuration ⚙️

The following environment variables are required:

| Variable | Description | Example |
|---|---|---|
| `JWT_SECRET` | Secret key for JWT signing (min. 32 characters) | `my-super-secret-key-32-chars!!` |
| `ADMIN_MAIL` | Email for the default admin user | `admin@example.com` |
| `ADMIN_PASSWORD` | Password for the default admin user | `admin123` |

You can check `application.properties.example` for reference.

## Endpoints 🛣️

### Auth (public)

| Method | Endpoint | Description | Body |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | `{ "email": "", "password": "" }` |
| POST | `/api/auth/login` | Login and get JWT token | `{ "email": "", "password": "" }` |

Both endpoints return:
```json
{
  "token": "eyJhbGci..."
}
```

### Demo (protected) 🔒

Include the token in the `Authorization` header: `Bearer <token>`

| Method | Endpoint | Role required | Description |
|---|---|---|---|
| GET | `/api/demo` | USER, ADMIN | Returns a demo message |
| GET | `/api/demo/admin` | ADMIN only | Returns an admin-only message |

## Common Issues 🚨

**WeakKeyException on startup**
Your `JWT_SECRET` is too short. It must be at least 32 characters.

**401 Unauthorized on protected endpoints**
Make sure you are sending the token in the `Authorization` header with the format `Bearer <token>`.

**Port already in use**
Another process is using port 8080. Stop it or change the port with `-p 9090:8080` in the Docker run command.

