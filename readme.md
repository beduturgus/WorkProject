Application goal to reach following requirements:

**Functional requirements:**

1. Expose endpoint to consume xml payload.
2. Validate payload against xsd schema(resources/contactsInfoSchema.xsd) and if invalid payload is received - deny it.
3. If received paylaod have any empty fields and default value to it(String - generatedValue).
4. Store received data in database.
5. Convert payoad in to JSON format and store it in folder(data/routes/route_output)

**Tech stack:**

Apache Camel 2.24.0,

Spring Boot,

Postgresql,

Spring security 5.1.5,

SLF4J 1.7.29,

GSON 2.8.6,

**Application Security**

All HTTP requests received by application will be authenticated.
In order to Spring-security to authorize the request user first must acquire cookie by running "./data/scripts/refresh_cookie.sh". Script includes cookie storage path, so this need to be adjusted during initial setup.
After cookie was acquired request can be send using "./data/scripts/send_payload.sh". Scripts also includes path to cookie storage so this need to be adjusted according to received cookie path. 
Currently only one user is set up:
Username: "username"
Password: "password"

**Database setup**

If running application for the first time it will require running downloading postgres database server, creating database and table for the data.
1. To create data base run "./data/scripts/initdb_linux.sh" (Only works on linux based systems, for other operating systems detailed steps can be found here https://tableplus.com/blog/2018/10/how-to-start-stop-restart-postgresql-server.html)
2. To create table run "./data/scripts/createTable.sh" and when promted for pasword enter "postgres"
3. Before application is started make sure postgres server is active. This can be done with "sudo service postgresql status"