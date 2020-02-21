## Spring Security OAuth - New Stack

### Relevant information:

1. `new-auth-server` is a Keycloak Authorization Server wrapped as a Spring Boot application
2. There is one OAuth Client registered in the Authorization Server:
   1. Client Id: newClient
   2. Client secret: newClientSecret
   3. Redirect Uri: http://localhost:8082/new-client/login/oauth2/code/custom
3. There are two users registered in the Authorization Server:
   1. john@test.com / 123
   2. mike@other.com / pass
    