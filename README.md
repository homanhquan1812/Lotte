# Tech Stack
- Java 17.
- Spring Boot v4.10, Security (JWT), Data JPA, PostgreSQL, MapStruct, Lombok, Maven, Flyway.
- Monolithic Architecture.

# How to run
Run `mvn clean install`.
Connect to PostgreSQL with both username and password are `postgres`, database's name is `lotte`.
Account Info:
- ADMIN -> username `admin` & password `admin123`.
- STAFF -> username `staff1` & password `staff123`.

# Endpoints
- [POST] `/api/v1/auth/login`: Login.
- [GET] `/api/v1/user?...=...&...=...` (Param): User pageable list.
- [GET] `/api/v1/user/{userId}`: Find user by Id.
- [POST] `/api/v1/user`: Create a new user
- [PATCH] `/api/user/{userId}`: Update user info by Id.
- [DELETE] `/api/user/{userId}`: Delete user by Id.
- [GET] `/api/v1/document?...=...&...=...` (Param): Document pageable list
- [GET] `/api/v1/document/{id}`: Find a document by Id.
- [POST] `/api/v1/document`: Create a new document.
- [PATCH] `/api/v1/document/{id}`: Update a document partially by Id.
- [DELETE] `/api/v1/document/{id}`: Delete a document by Id.
- [GET] `/api/v1/document/reports/status-count`: Count document by Status.

# Functions not yet implemented
- File upload/attachment for a document.
