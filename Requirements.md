# General
- [ ] Unit tests for service and controller layers via JUnit + Mockito
# Functional requirements
## Auth and security
- [ ] User login and registration via Spring Security
- [ ] Password encryption via BCrypt
- [ ] Role-based authorization
- [ ] Session-based authentication (jwt?) + custom login page
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
- [ ] Prevent duplicate applications using validation or a unique constraint in the database
## Validation
- [ ] Use `@Valid` `@NotBlank` `@Email` `@Size` etc.
- [ ] Display errors on thymeleaf forms
## Exception handling
- [ ] Global exception handler (@ControllerAdvice)
- [ ] JobNotFoundException, UserNotFoundException, etc.
- [ ] Return user-friendly error pages
# Non-functional requirements
- [ ] Password encryption and role-based access
- [ ] Efficient CRUD operations
- [ ] User-friendly UI
- [ ] Layered architecture
- [ ] "Clean" code style
- [ ] Database supports multiple users and jobs
# Roles
## Admin
- [ ] Approve/reject job postings
- [ ] View all user accounts
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