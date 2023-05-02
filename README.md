## Bank's backend services

Services with strange requirements for backend part of bank project.

> In this case, backend service - service,
that doesn't have a GUI.
Frontend services is a services with GUI,
and they can use backend services, not vise versa.

### Deploying

If you want to use this backend serves, 
you have to run docker-compose files.

_First of all, we have to run databases:_

```
$ docker compose -f .infra/dc-databases.yml up -d
```

_After that,
since our application using kafka_:

```
$ docker compose -f .infra/dc-kafka.yml up -d
```

> IMPORTANT. Make sure that  all services are running.

_Finally, start zipkin_:

```
$ docker compose -f .infra/dc-zipkin.yml up -d
```

_After that you can run main services._

### Swagger

_Accounts service:_
```
$ http://localhost:8080/api/v2/accounts/swagger-ui/index.html
```

_Loans service:_
```
$ http://localhost:8080/api/v2/loans/swagger-ui/index.html
```

_Users service also have swagger, but you can't use this service for now,
but if you want, yu have to create user in database with bcrypto encoded password:_
```
http://localhost:8000/api/v2/users/swagger-ui/index.html
```

 If you don't use gateway, change ports
 - Loans service    - 8181
 - Accounts service - 8081
### Future

- Use security to protect resource servers and so on.
Now it's impossible to test and, because of that, not done.
- Carry out tests from archive repositories.
- May be use k8s
- Refactoring
