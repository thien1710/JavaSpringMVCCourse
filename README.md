# Esm Project

Build Restful CRUD API for a blog using Spring Boot, Mysql, JPA and Hibernate.

### Users

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /rest/v1/entities/users | Add user with role based (ADMIN) | [JSON](#addUserWithRoleBased) |
| POST   | /rest/v1/entities/auth/signup | Add user without role based (ADMIN or USER) | [JSON](#addUserWithoutRoleBased) |
| POST   | /rest/v1/entities/auth/signin | Login to created account (ADMIN or USER) | [JSON](#userLogin) |
| GET    | /rest/v1/entities/users/{id} | Get user by id (ADMIN or USER) | [JSON](#getUserById) |
| GET    | /rest/v1/entities/users/me | Get current user (USER) | [JSON](#getCurrentUser) |
| PUT    | /rest/v1/entities/users/{username} | Update user (ADMIN or USER) | [JSON](#updateUser) |
| PUT    | /rest/v1/entities/users/{username}/giveAdmin | Give admin role (ADMIN) | [JSON](#giveAdmin) |
| PUT    | /rest/v1/entities/users/{username}/takeAdmin | Take admin role (ADMIN) | [JSON](#takeAdmin) |
| DELETE | /rest/v1/entities/users/{username} | Delete user (ADMIN or USER) | [JSON](#deleteUser) |

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

##### <a id="(addUserWithoutRoleBased) ">Add user without role based (ADMIN or USER) -> /rest/v1/entities/auth/signup</a>
```json
{
	"firstName": "Leanne",
	"lastName": "Graham",
	"username": "leanne2",
	"password": "password",
	"email": "leanne2.graham@gmail.com"
}
```

##### <a id="(userLogin) ">Login to created account (ADMIN or USER) -> /rest/v1/entities/auth/signin -> Generate token to response body</a>
```json
{
    "username":"mrthien3",
    "password":"password"
}
```

##### <a id="addUserWithRoleBased">Add user with role based (ADMIN) -> /rest/v1/entities/users -> Take token from login request and add to the header authorization</a>
```json
{
	"firstName": "Thien",
	"lastName": "Nguyen",
	"username": "mrthien4",
	"password": "password",
	"email": "mrthien4@gmail.com",
    "departments": [
        {
            "departmentName": "Victor Plains",
            "qty": "Suite 879",
            "note": "Wisokyburgh"
	    }
    ] 
}
```

##### <a id="(getUserById) ">Get user by id (ADMIN or USER) -> /rest/v1/entities/users/{id} -> Take token from login request and add to the header authorization</a>

##### <a id="(getCurrentUser) ">Get current user (USER) -> /rest/v1/entities/users/me -> Take token from login request and add to the header authorization</a>

##### <a id="(updateUser) ">Update user (ADMIN or USER) -> /rest/v1/entities/users/{username} -> Take token from login request and add to the header authorization</a>
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

##### <a id="(giveAdmin) ">Give admin role (ADMIN) -> /rest/v1/entities/users/{username}/giveAdmin -> Take token from login request and add to the header authorization</a>

##### <a id="((takeAdmin) ) ">Take admin role (ADMIN) -> /rest/v1/entities/users/{username}/takeAdmin -> Take token from login request and add to the header authorization</a>

##### <a id="((deleteUser) ) ">Delete user (ADMIN or USER) -> /rest/v1/entities/users/{username} -> Take token from login request and add to the header authorization</a>

![segment](https://api.segment.io/v1/pixel/track?data=ewogICJ3cml0ZUtleSI6ICJwcDJuOTU4VU1NT21NR090MWJXS0JQd0tFNkcydW51OCIsCiAgInVzZXJJZCI6ICIxMjNibG9nYXBpMTIzIiwKICAiZXZlbnQiOiAiQmxvZ0FwaSB2aXNpdGVkIiwKICAicHJvcGVydGllcyI6IHsKICAgICJzdWJqZWN0IjogIkJsb2dBcGkgdmlzaXRlZCIsCiAgICAiZW1haWwiOiAiY29tcy5zcHVyc0BnbWFpbC5jb20iCiAgfQp9)
