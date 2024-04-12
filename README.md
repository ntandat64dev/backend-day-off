A Spring Boot project that manage employee day offs.

Run: `java -jar back-end-day-off-0.0.1-SNAPSHOT.jar`

Database console: http://localhost:8080/h2-console

- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:dacnpm`
- User Name: `sa`
- Password:

### Database Schema

<img src="./screenshots/schema.png" width="1465" alt="">

`role`

- 0 - Manager
- 1 - Admin
- 2 - User

`status`

- 0 - Accepted
- 1 - Rejected
- 2 - Waiting

#### Sample data

**Users**

<img src="./screenshots/data/users.png" width="1090" alt="">

**Departments**

<img src="./screenshots/data/departments.png" width="79" alt="">

**LeaveRemains**

<img src="./screenshots/data/leave_remains.png" width="234" alt="">

**LeaveRequests**

<img src="./screenshots/data/leave_requests.png" width="100%" alt="">
