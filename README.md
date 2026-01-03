# User Management API

Spring Boot REST API with JWT authentication and role-based access control.

## Quick Start

```bash
# Create database
psql -c "CREATE DATABASE user_crud_db;"

# Run application
./gradlew bootRun
```

Default admin: `admin@gmail.com` / `admin123`

## Roles

| Role  | View Users | Create/Update/Delete |
| ----- | ---------- | -------------------- |
| USER  | Yes        | No                   |
| ADMIN | Yes        | Yes                  |

## Endpoints

### Auth

```
POST /api/auth/register   - Register new account (role: USER)
POST /api/auth/login      - Login, returns JWT token
POST /api/auth/refresh    - Refresh access token
POST /api/auth/logout     - Logout
```

### Users

```
GET    /api/users         - List users (USER, ADMIN)
GET    /api/users/{id}    - Get user by ID (USER, ADMIN)
POST   /api/users         - Create user (ADMIN only)
PATCH  /api/users/{id}    - Update user (ADMIN only)
DELETE /api/users/{id}    - Delete user (ADMIN only)
```

## Example

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@gmail.com","password":"admin123"}'

# Use token
curl http://localhost:8080/api/users \
  -H "Authorization: Bearer <TOKEN>"
```

## Swagger UI

Access API documentation at: http://localhost:8080/swagger-ui.html

To test protected endpoints:

1. Login via `/api/auth/login`
2. Copy the `accessToken` from response
3. Click "Authorize" button in Swagger
4. Enter: `Bearer <TOKEN>`
