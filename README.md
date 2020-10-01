
# Esm System
## aa
### 111
#### a

# Spring Boot, MySQL, Spring Security, JWT, JPA, Rest API

Build Restful CRUD API for a ESM System using Spring Boot, Mysql, JPA and Hibernate.

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/thien1710/JavaSpringMVCCourse.git
```

**2. Create Mysql database**
```bash
create database test
```
- run `src/main/resources/esm.sql`

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**

```bash

```
The app will start running at <http://localhost:8090>

## All APIs URL

| Index | Name | View |
| ------ | ------ | ------ | 
| 1 | User | [JSON](#user) |
| 1.1 | Login | [JSON](#login) |
| 1.2 | Signup | [JSON](#signup) |
| 1.3 | Add User | [JSON](#addUser) |
| 1.4 | Get Current User | [JSON](#getCurrentUser) |
| 1.5 | Forgot Password | [JSON](#forgetPassword) |
| 1.6 | Reset Password | [JSON](#resetPassword) |
| 1.7 | Update user | [JSON](#updateUser) |
| 1.8 | Delete User | [JSON](#deleteUser) |
| 1.9 | Give Admin | [JSON](#giveAdmin) |
| 1.10 | Take Admin | [JSON](#takeAdmin) |
| 1.11 | Search User | [JSON](#searchUser) |
| 2 | Customer | [JSON](#customer) |
| 2.1 | Add Customer | [JSON](#addCustomer) |
| 2.2 | Update Customer | [JSON](#updateCustomer) |
| 2.3 | Delete Customer | [JSON](#deleteCustomer) |
| 2.4 | Search Customer | [JSON](#searchCustomer) |
| 3 | Project | [JSON](#project) |
| 3.1 | Add Project | [JSON](#addProject) |
| 3.2 | Update Project | [JSON](#updateProject) |
| 3.4 | Delete Project | [JSON](#deleteProject) |
| 3.4 | Search Project | [JSON](#searchProject) |


## Explore Rest APIs

The app defines following CRUD APIs.

## <a id="user">1. Users</a>

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

### <a id="login">1. Login</a>

**URL** : http://localhost:8090/api/v1/auth/signin/

**Method :** POST

**Description :** Login

**Header :**

**Parameter :**

**Request Body :**
```json
{
    "username":"leanne",
    "password":"P@ssw0rd"
}
```

**Response Body :**
```json
{"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFubmUiLCJzY29wZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImlhdCI6MTYwMTUyNzU4MiwiZXhwIjoxNjAxNTQ1NTgyfQ.AD2_2SLXEa2P88EW0N9m43iTpHYgfHkDkDRtc2Gfyzc"}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="signup">2. Signup</a>

**URL** : http://localhost:8090/api/v1/auth/signup

**Method :** POST

**Description :** Signup

**Header :**

**Parameter :**

**Request Body :**
```json
{
	"firstName": "leanne12",
	"lastName": "jack",
	"username": "leanne13",
	"password": "P@ssw0rd",
	"email": "leanne13.graham@gmail.com"
}
```

**Response Body :**
```json
{
    "success": true,
    "message": "User registered successfully"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="addUser">3. Add User</a>

**URL** : http://localhost:8090/api/v1/users

**Method :** POST

**Description :** Add user

**Header :** Bearer {token Admin}

**Parameter :**

**Request Body :**
```json
{
	"firstName": "Thien",
	"lastName": "Nguyen",
	"username": "thien1710",
	"password": "P@ssw0rd",
	"email": "nguyen.tran.nhat.thien1710@gmail.com",
    "role": "USER",
    "departmentId": 1
}

```

**Response Body :**
```json
{
    "success": true,
    "message": "User registered successfully"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### 4. <a id="getCurrentUser">Get Current User</a>

**URL** : http://localhost:8090/api/v1/users/me

**Method :** GET

**Description :** Get Current User

**Header :** 

**Authorization** : Bearer {token}

**Parameter :** Bearer {token Admin}

**Request Body :**
```

**Response Body :**
```json
{
    "id": 1,
    "userIdHash": "zBaIw5o1xg5211c3Oocn6DiuWRm8t3",
    "firstName": "leanne",
    "lastName": "graham",
    "username": "leanne",
    "email": "leanne.graham@gmail.com",
    "address": null,
    "phone": "0001",
    "website": null,
    "salaryNum": null,
    "roles": [
        {
            "id": 1,
            "name": "ADMIN"
        },
        {
            "id": 2,
            "name": "USER"
        }
    ],
    "departments": []
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```









### 5. <a id="forgetPassword">Forget Password</a>

**URL** : http://localhost:8090/api/v1/users/password-forgot-request

**Method :** POST

**Description :** Forget Password

**Header :** Reset Password

**Parameter :**

**Request Body :**
```json
{
    "email": "leanne.graham@gmail.com"
}
```

**Response Body :**
```json
{
    "responseStatus": "SUCCESS",
    "description": "REQUEST_PASSWORD_RESET_SUCCESSFUL",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFubmUiLCJleHAiOjE2MDUxMzI1MDZ9.K8I1ow96KehiidNL3O8RJFfYsjbMuF0_oQw9M8U1uO4"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="resetPassword">6. Reset Password</a>

**URL** : http://localhost:8090/api/v1/users/forgot-password/reset

**Method :** PUT

**Description :** Reset Password

**Header :** Reset Password

**Parameter :** 

    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFubmUiLCJleHAiOjE2MDUxMzQxMTR9.gfTc_fVnYcdIDUy8mU5Jy8kqkhmASezuE-eueYob8j4"
    password: "P@ssw0rd"

**Request Body :**

**Response Body :** "updatePasswordSUCCESSFUL"

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="deleteUser">7.  Update user</a>

**URL** : http://localhost:8090/api/v1/users/{username}

**Method :** PUT

**Description :** Update user

**Header :** Bearer {token}

**Parameter :**

**Request Body :**
```json
{
	"firstName": "1111111111111111111111Thien",
	"lastName": "Nguyen",
	"username": "mrthien1",
	"password": "password",
	"email": "mrthien2@gmail.com",
    "departments": [
        {
            "departmentName": "Victor Plains 2",
            "qty": "Suite 879",
            "note": "Wisokyburgh"
	    }
    ] 
}
```

**Response Body :**
```json
{
    "id": 9902,
    "userIdHash": "9zhXQiuzoizHzYAkrNQT6OmKktK0ti",
    "firstName": "1111111111111111111111Thien",
    "lastName": "Nguyen",
    "username": "mrthien1",
    "email": "leanne13.graham@gmail.com",
    "address": null,
    "phone": null,
    "website": null,
    "salaryNum": null,
    "roles": [
        {
            "id": 2,
            "name": "USER"
        }
    ],
    "departments": []
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### 8. <a id="giveAdmin">Delete User</a>

**URL** : http://localhost:8090/api/v1/users/{UserId}

**Method :** DELETE

**Description :** Delete User

**Header :** 

**Authorization** :

**Parameter :** Bearer {token Admin}

**Request Body :**

**Response Body :**
```json
{
    "success": true,
    "message": "deleteSUCCESSFUL"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="giveAdmin">9. Give Admin</a>

**URL** : http://localhost:8090/api/v1/users/{UserId}/giveAdmin

**Method :** PUT

**Description :** Give Admin

**Header :** Bearer {token Admin}

**Parameter :**

**Request Body :**

**Response Body :**
```json
{
    "success": true,
    "message": "GIVE_ADMIN_ROLE_TO_USER_SUCCESSFUL"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="takeAdmin">10. Take Admin</a>

**URL** : http://localhost:8090/api/v1/users/{UserId}/giveAdmin

**Method :** PUT

**Description :** Take Admin

**Header :** Bearer {token Admin}

**Parameter :**

**Request Body :**

**Response Body :**
```json
{
    "success": true,
    "message": "TAKE_ADMIN_ROLE_FROM_USER_SUCCESSFUL"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="searchUser">11. Search User</a>

**URL** : http://localhost:8090/api/v1/users/search

**Method :** GET

**Description :** Search User

**Header :** Bearer {token}

**Parameter :**

**Request Body :**
```json
{
    "searchCondition": {
        "userSearchCondition": {
            "id": 9900
        }
    }
}
```

**Response Body :**
```json
[
    {
        "id": 9900,
        "userIdHash": "b2ba59b1e30926257e797fee9cad597848227fa1",
        "firstName": "Brando",
        "lastName": "Bins",
        "username": "salma37",
        "email": "aheller@example.org",
        "address": "304 Baumbach Shoals Suite 276\nNorth Katrine, VT 87563",
        "phone": "251-165-3728",
        "website": "http://rosenbaumlabadie.com/",
        "salaryNum": 0,
        "roles": [
            {
                "id": 2,
                "name": "USER"
            }
        ],
        "departments": []
    }
]
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```








## <a id="customer">2. Customer</a>

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

### <a id="addCustomer">1. Add Customer</a>

**URL** : http://localhost:8090/api/v1/customers

**Method :** POST

**Description :** Add Customer

**Header :** Baearer (token User)

**Parameter :**

**Request Body :**
```json
{
    "customerName":"project name"
}
```

**Response Body :**
```json
{
    "customerName":"project name"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="updateCustomer">2. Update Customer</a>

**URL** : http://localhost:8090/api/v1/customers/{CustomerId}

**Method :** PUT

**Description :** Update Customer

**Header :** Bearer (token User)

**Parameter :**

**Request Body :**
```json
{
    "customerName":"updated project name"
}
```

**Response Body :**
```json
{
    "id": 9811,
    "customerName": "updated project name",
    "phone": null,
    "email": null,
    "address": null,
    "filed": null,
    "note": null
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="deleteCustomer">3. Delete Customer</a>

**URL** : http://localhost:8090/api/v1/customers/{CustomerId}

**Method :** DELETE

**Description :** Delete Customer

**Header :** Bearer {token User}

**Parameter :**

**Request Body :**

**Response Body :**
```json
{
    "success": true,
    "message": "You successfully deleted post"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### <a id="searchCustomer">4. Search Customer</a>

**URL** : http://localhost:8090/api/v1/customers/search

**Method :** GET

**Description :** Get Current User

**Header :** Bearer {token}

**Parameter :**

**Request Body :**
```json
{
    "searchCondition": {
        "userSearchCondition": {
            "username": "leanne"
    
        },
        "customerSearchCondition": {
            "customerName":"a",
            "id": 4
        }
    }
}
```

**Response Body :**
```json
[
    {
        "id": 4,
        "customerName": "April",
        "phone": null,
        "email": null,
        "address": null,
        "filed": null,
        "note": null
    }
]
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

## <a id="project">3. Project</a>

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

### <a id="addProject">1. Add Project</a>

**URL** : http://localhost:8090/api/v1/customers/{CustomerId/projects

**Method :** POST

**Description :** Add Project

**Header :** Baearer (token User)

**Parameter :**

**Request Body :**
```json
{
    "projectName":"this is exactly project name 3"
}
```

**Response Body :**
```json
{
    "id": 9811,
    "projectName": "this is exactly project name 3",
    "startTime": null,
    "finishtTime": null,
    "totalTime": null,
    "budget": null,
    "status": null,
    "note": null
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### 2. <a id="updateProject">Update Project</a>

**URL** : http://localhost:8090/api/v1/customers/{CustomerId}/projects/{ProjectId}

**Method :** PUT

**Description :** Update Project

**Header :** Bearer (token)

**Parameter :**

**Request Body :**
```json
{
    "projectName": "ypdated this is exactly updated project name"
}
```

**Response Body :**
```json
{
    "id": 1,
    "projectName": "ypdated this is exactly updated project name",
    "startTime": "2020-08-16T17:00:00.000+00:00",
    "finishtTime": "2020-10-10T16:59:59.000+00:00",
    "totalTime": 150,
    "budget": null,
    "status": null,
    "note": null
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### 3. <a id="deleteProject">Delete Project</a>

**URL** : http://localhost:8090/api/v1/customers/{CustomerId}/projects/{ProjectId}

**Method :** DELETE

**Description :** Delete Project

**Header :** Bearer {token User}

**Parameter :**

**Request Body :**

**Response Body :**
```json
{
    "success": true,
    "message": "deleteProjectSUCCESSFUL"
}
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```

### 4. <a id="searchProject">Search Project</a>

**URL** : http://localhost:8090/api/v1/customers/search

**Method :** GET

**Description :** Get Current Project

**Header :** Bearer {token}

**Parameter :**

**Request Body :**
```json
{
    "searchCondition": {
        "userSearchCondition": {
            "username": "leanne"
    
        },
        "customerSearchCondition": {
            "customerName":"a",
            "id": 4
        }
    }
}
```

**Response Body :**
```json
[
    {
        "id": 4,
        "customerName": "April",
        "phone": null,
        "email": null,
        "address": null,
        "filed": null,
        "note": null
    }
]
```

FAILD :
```json
{
	"timestamp" : "",
	"message" :  result (error),
	"details" :	uri (url) .
}
```












