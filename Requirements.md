# General
- [x] Unit tests for service and controller layers via JUnit + Mockito
# Functional requirements
## Auth and security
- [x] User login and registration via Spring Security
- [x] Password encryption via BCrypt
- [x] Role-based authorization
- [x] Session-based authentication (jwt?)
- [x] custom login page
## Jobs module
- [x] Employer creates jobs with:
    - [x] Title
    - [x] Description
    - [x] Salary
    - [x] Location
    - [x] Category
    - [x] Deadline
- [x] Jobs remain pending until approved by an admin
## Application module
- [x] Prevent duplicate applications using validation or a unique constraint in the database
## Validation
- [x] Use `@Valid` `@NotBlank` `@Email` `@Size` etc.
- [x] Display errors on thymeleaf forms
## Exception handling
- [x] Global exception handler (@ControllerAdvice)
- [x] JobNotFoundException, UserNotFoundException, etc.
- [x] Return user-friendly error pages
# Non-functional requirements
- [x] Password encryption and role-based access
- [x] Efficient CRUD operations
- [x] User-friendly UI
- [x] Layered architecture
- [x] "Clean" code style
- [x] Database supports multiple users and jobs
# Roles
## Admin
- [x] Approve/reject job postings
- [x] View all user accounts
- [x] Activate/deactivate users
## Employer
- [x] Register + log in
- [x] Create new job postings
- [x] Edit/update/delete job postings
- [x] View applicatoions submitted by students
## Student
- [x] Register + log in
- [x] View only admin-approved jobs
- [x] View detailed job descriptions
- [x] Apply to jobs (once per job)