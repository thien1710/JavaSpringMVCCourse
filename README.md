# Esm Project

Build Restful CRUD API for a blog using Spring Boot, Mysql, JPA and Hibernate.

### Users

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /rest/v1/entities/users | Add user with authorization (ADMIN) | [JSON](#usercreate) |
| POST   | /rest/v1/entities/auth/signup | Add user without authorization (ADMIN or USER) | [JSON](#usercreate) |
| POST   | /rest/v1/entities/auth/signin | Login to created account (ADMIN or USER) | [JSON](#userlogin) |
| GET    | /rest/v1/entities/users/{id} | Get user by id (ADMIN or USER) | |
| GET    | /rest/v1/entities/users/me | Get current user (USER) | |
| PUT    | /rest/v1/entities/users/{username} | Update user (ADMIN or USER) | [JSON](#updateuser) |
| PUT    | /rest/v1/entities/users/{username}/giveAdmin | Give admin role (ADMIN) | [JSON](#updateuser) |
| PUT    | /rest/v1/entities/users/{username}/takeAdmin | Take admin role (ADMIN) | [JSON](#updateuser) |
| DELETE | /rest/v1/entities/users/{username} | Delete user (ADMIN or USER) | |

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

##### <a id="usercreate">Sign Up -> /rest/v1/entities/users</a>
```json
{
	"firstName": "Thien",
	"lastName": "Nguyen",
	"username": "mrthien",
	"password": "p@ssw0rd",
	"email": "mrthien@gmail.com"
}
```

##### <a id="userlogin">Log In -> /rest/v1/entities/users/login</a>
```json
{
	"email": "mrthien@gmail.com",
	"password": "p@ssw0rd"
}
```

##### <a id="getuserbyid">Get User -> /rest/v1/entities/users/L9zbwactEBoM97pw5ndamRZ1aHKy8J</a>
```json
{
	"userID": "L9zbwactEBoM97pw5ndamRZ1aHKy8J",
	"firstname": "thien",
	"lastname": "nguyen",
	"email": "mrthien@gmail.com",
}
```

##### <a id="updateuser">Update User -> /rest/v1/entities/users/L9zbwactEBoM97pw5ndamRZ1aHKy8J</a>
```json
{
	"userID": "L9zbwactEBoM97pw5ndamRZ1aHKy8J",
	"firstname": "updated_thien",
	"lastname": "nguyen",
	"email": "mrthien@gmail.com",
}
```

##### <a id="deleteuser">Delete User -> /rest/v1/entities/users/L9zbwactEBoM97pw5ndamRZ1aHKy8J</a>
```json
{
	 "operationResult": "SUCCESS",
   "operationName": "DELETE"
}
```

##### <a id="filteruser">Filter User -> /rest/v1/entities/users/search?keyword=mr</a>
```json
[
    {
        "userID": "ojbH273lPlstoYu5eaBRjLoYDH53kZ",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien1@gmail.com"
    },
    {
        "userID": "Zax9oMzF55fmzAM81y4CTJ1sdPg8xx",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien1.com"
    },
    {
        "userID": "QdDiSUkzGXkLZTqG8X0XeVlMbdEYia",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien2@gmail.com.com"
    },
    {
        "userID": "9zpWsb6tl1FCsJLzRLNI8nFg7hOLPX",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien3@gmail.com.com"
    },
    {
        "userID": "rxRY5Sj4tlbG5NmeIe1jGTsV9TDWyR",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien4@gmail.com.com"
    }
]
```

##### <a id="paginguser">Paging User -> /rest/v1/entities/users?page=1&limit=4</a>
```json
[
    {
        "userID": "ojbH273lPlstoYu5eaBRjLoYDH53kZ",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien1@gmail.com"
    },
    {
        "userID": "Zax9oMzF55fmzAM81y4CTJ1sdPg8xx",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien1.com"
    },
    {
        "userID": "QdDiSUkzGXkLZTqG8X0XeVlMbdEYia",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien2@gmail.com.com"
    },
    {
        "userID": "9zpWsb6tl1FCsJLzRLNI8nFg7hOLPX",
        "firstname": "thien",
        "lastname": "nguyen",
        "email": "mrthien3@gmail.com.com"
    }
]
```

![segment](https://api.segment.io/v1/pixel/track?data=ewogICJ3cml0ZUtleSI6ICJwcDJuOTU4VU1NT21NR090MWJXS0JQd0tFNkcydW51OCIsCiAgInVzZXJJZCI6ICIxMjNibG9nYXBpMTIzIiwKICAiZXZlbnQiOiAiQmxvZ0FwaSB2aXNpdGVkIiwKICAicHJvcGVydGllcyI6IHsKICAgICJzdWJqZWN0IjogIkJsb2dBcGkgdmlzaXRlZCIsCiAgICAiZW1haWwiOiAiY29tcy5zcHVyc0BnbWFpbC5jb20iCiAgfQp9)
