A Spring Boot REST API project that manages employee day offs.

Database console: `server_url:8080/h2-console`

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

<img src="./screenshots/data/leave_requests.png" width="565" alt="">

# APIs

## Summary

- [Login](#login): `POST /api/v1/auth/login`
- [Register leave request](#register-leave-request): `POST /api/v1/leave_request`
- [Confirm leave request](#confirm-leave-request): `PUT /api/v1/leave_request/update_status/{userId}`
- [Get user info](#get-user-info): `GET /api/v1/users/{userId}`
- [Get leave request history for user](#get-leave-request-history-for-user): `GET /api/v1/leave_request/pagination_with_userid/{userId}`
- [Get all leave requests](#get-all-leave-requests): `GET /api/v1/leave_request/pagination`
- [Get leave requests by search keyword](#get-leave-requests-by-search-keyword): `GET /api/v1/leave_request/pagination_by_search/{keyword}}`

## Login

`POST /api/v1/auth/login`

**Request body**

```json
{
  "email": "user1@gmail.com",
  "password": "00000000"
}
```

**Response**

`200 - Ok`

```json
{
  "id": 3,
  "email": "user1@gmail.com",
  "fullName": "Maxwell Salazar",
  "birthdate": "1997-01-11",
  "workDate": "2023-06-10",
  "phone": "0123456789",
  "address": "21788 Leffler Vista Suite 094",
  "role": 2,
  "department": {
    "id": 1,
    "name": "IT"
  },
  "remainDays": 21
}
```

---

## Register leave request

`POST /api/v1/leave_request`

**Request body**

```json
{
  "userId": 3,
  "startDate": "2024-05-01",
  "endDate": "2024-05-03",
  "createdAt": "2024-04-11T09:13:00",
  "reason": "Sick"
}
```

**Response**

`201 - Created`

```json
{
  "id": 13,
  "startDate": "2024-05-01",
  "endDate": "2024-05-03",
  "createdAt": "2024-04-11T09:13:00",
  "createdBy": 3,
  "reason": "Sick",
  "status": 2
}
```

---

## Confirm leave request

`PUT /api/v1/leave_request/update_status/{userId}`

**Request body**

`200 - Ok`

```json
{
  "id": 3,
  "status": 0,
  "manager_id": 0
}
```

**Response**

```json
{
  "id": 3,
  "status": 0,
  "manager_id": 3
}
```

---

## Get user info

`GET /api/v1/users/{userId}`

**Response**

`200 - Ok`

```json
{
  "id": 3,
  "email": "user1@gmail.com",
  "fullName": "Maxwell Salazar",
  "birthdate": "1997-01-11",
  "workDate": "2023-06-10",
  "phone": "0123456789",
  "address": "21788 Leffler Vista Suite 094",
  "role": 2,
  "department": {
    "id": 1,
    "name": "IT"
  },
  "remainDays": 21
}
```

---

## Get leave request history for user

`GET /api/v1/leave_request/pagination_with_userid/{userId}`

**Response**

`200 - Ok`

```json
{
  "content": [
    {
      "id": 1,
      "startDate": "2023-08-15",
      "endDate": "2023-08-18",
      "reason": "Sick",
      "status": 0,
      "createdAt": "2023-08-10T09:12:00",
      "username": "Maxwell Salazar",
      "manager": "Craig Berry"
    },
    {
      "id": 2,
      "startDate": "2024-03-03",
      "endDate": "2024-03-10",
      "reason": "Wife gives birth",
      "status": 0,
      "createdAt": "2024-03-02T08:20:00",
      "username": "Maxwell Salazar",
      "manager": "Craig Berry"
    },
    {
      "id": 3,
      "startDate": "2024-05-10",
      "endDate": "2024-05-12",
      "reason": "Visit family",
      "status": 1,
      "createdAt": "2024-04-04T12:38:00",
      "username": "Maxwell Salazar",
      "manager": "Craig Berry"
    }
  ],
  "pageNo": 0,
  "pageSize": 15,
  "totalElements": 4,
  "totalPages": 1,
  "last": true
}
```

---

## Get all leave requests

`GET /api/v1/leave_request/pagination`

**Response**

`200 - Ok`

```json
{
  "content": [
    {
      "id": 1,
      "startDate": "2023-08-15",
      "endDate": "2023-08-18",
      "reason": "Sick",
      "status": 0,
      "createdAt": "2023-08-10T09:12:00",
      "username": "Maxwell Salazar",
      "manager": "Craig Berry"
    },
    {
      "id": 2,
      "startDate": "2024-03-03",
      "endDate": "2024-03-10",
      "reason": "Wife gives birth",
      "status": 0,
      "createdAt": "2024-03-02T08:20:00",
      "username": "Maxwell Salazar",
      "manager": "Craig Berry"
    }
  ],
  "pageNo": 0,
  "pageSize": 2,
  "totalElements": 13,
  "totalPages": 7,
  "last": false
}
```

---

## Get leave requests by search keyword

`GET /api/v1/leave_request/pagination_by_search/{keyword}}`

**Response**

`200 - Ok`

```json
{
  "content": [
    {
      "id": 6,
      "startDate": "2024-03-07",
      "endDate": "2024-03-08",
      "reason": "Dating",
      "status": 0,
      "createdAt": "2024-03-05T10:55:00",
      "username": "Alec Holden",
      "manager": "Craig Berry"
    },
    {
      "id": 8,
      "startDate": "2024-06-25",
      "endDate": "2024-06-26",
      "reason": "Dating",
      "status": 2,
      "createdAt": "2024-06-21T17:03:00",
      "username": "Floyd Hayward",
      "manager": "Craig Berry"
    }
  ],
  "pageNo": 0,
  "pageSize": 2,
  "totalElements": 2,
  "totalPages": 1,
  "last": true
}
```