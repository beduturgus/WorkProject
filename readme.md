Application goal to reach following requirements:

Functional requirements:

1. Expose endpoint to consume xml payload.
2. Validate payload against xsd schema(resources/contactsInfoSchema.xsd) and if invalid payload is received - deny it.
3. If received paylaod have any empty fields and default value to it(String - generatedValue).
4. Store received data in database.
5. Convert payoad in to JSON format and store it in folder(data/routes/route_output)

Non-functional requirements:

1. Use Camel framework to implement this project.
2. Cover code with unit tests:
  a. Write test for each processor(src/main/java/my/project/BenasProject/routes/processors)
  b. Write test for whole route(src/main/java/my/project/BenasProject/routes/builders/HttpToDbRoute)


Starting database:

1. Make sure postgreSQL service is running `brew services start postgresql`
2. Start psql server which stores databases `pg_ctl -D /usr/local/var/postgres start`
3. Can list available databases with `psql -l`



