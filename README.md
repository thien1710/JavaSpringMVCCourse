
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
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

## 1. Users

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

### 1. Login

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

### 2. Signup

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

### 3. Add User

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

### 4. Get Current User

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









### 5. Forget Password

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

### 6. Reset Password

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

### 7.  User

**URL** : http://localhost:8090/api/v1/users/leanne13

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

### 8. Delete User

**URL** : http://localhost:8090/api/v1/users/me

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

### 8. Give Admin

**URL** : http://localhost:8090/api/v1/users/9900/giveAdmin

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

### 9. Take Admin

**URL** : http://localhost:8090/api/v1/users/9900/giveAdmin

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

### 10. Search User

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


enter code here




enter code here
