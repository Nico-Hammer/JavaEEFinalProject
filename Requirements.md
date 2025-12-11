# General
- [ ] Unit tests for service and controller layers via JUnit + Mockito
# Functional requirements
## Auth and security
- [ ] User login and registration via Spring Security
- [x] Password encryption via BCrypt
- [x] Role-based authorization
- [x] Session-based authentication (jwt?)
- [ ] custom login page
## Jobs module
- [ ] Employer creates jobs with:
    - [ ] Title
    - [ ] Description
    - [ ] Salary
    - [ ] Location
    - [ ] Category
    - [ ] Deadline
- [ ] Jobs remain pending until approved by an admin
## Application module
- [x] Prevent duplicate applications using validation or a unique constraint in the database
## Validation
- [x] Use `@Valid` `@NotBlank` `@Email` `@Size` etc.
- [ ] Display errors on thymeleaf forms
## Exception handling
- [x] Global exception handler (@ControllerAdvice)
- [x] JobNotFoundException, UserNotFoundException, etc.
- [ ] Return user-friendly error pages
# Non-functional requirements
- [x] Password encryption and role-based access
- [x] Efficient CRUD operations
- [ ] User-friendly UI
- [x] Layered architecture
- [x] "Clean" code style
- [x] Database supports multiple users and jobs
# Roles
## Admin
- [x] Approve/reject job postings
- [x] View all user accounts
- [ ] Activate/deactivate users
## Employer
- [ ] Register + log in
- [ ] Create new job postings
- [ ] Edit/update/delete job postings
- [ ] View applicatoions submitted by students
## Student
- [ ] Register + log in
- [ ] View only admin-approved jobs
- [ ] View detailed job descriptions
- [ ] Apply to jobs (once per job)