#SpringBoot User Management
Demo of Spring Boot REST API in test and production environments


## Following APIs have been implemented upon the given Swagger doc:
* "/users"     	GET: accept page and size to list users selectively.
* "/user"		PUT: with user object in body, to create a new User instance and return added instance.
* "/users/{id}" GET: get a user by ID
* "/users/{id}" POST: with user object in body to update it and return the updated instance.
* "/users/{id}" DELETE: delete concerned user.


## Datasource/Profiles
Two Databases (H2 and MySQL) are used for test and production environments.

FakeUserGenerator could be used to populate DB data with random name/email/password when:
* If "test" profile is selected.
* And if "-DinjectFakeUsers=true" has been specified when launching the app. "VM options" in IntelliJ.
* And the records in the H2 Database are less than 30 (for pagination tests)

## Global Exception Handling
No customer Exceptions defined, the JAVA IllegalArgumentException and NumberFormatException are captured as example.

## Repository and Service
UserService, backed by UserRepository, is used to implemented the 5 concerned business logics.

## Testing
Unit tests and Integration tests are created to validate Service and Controller respectively.