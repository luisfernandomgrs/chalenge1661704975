# Challenge of Bootcamp 3.0 - DevSuperior

This project is a Rest API, with the concept of layers trhought:

 ### Resources/Controller  <---  Service  <---  Repository  <---  Entity
 with use of the DTO's (Data Transfer Object) to give a reponse at endpoint

At challenge, I reviewed the content of the course, that was learned on class.
In this project I was restricted to that was required to exam, but was be a great experience and where I got some ideas to next projects.

Examples to use endpoint's :
```url
{{host}} is local system variable, that equal to: http://localhost:8080
```

### 1.  GET - All Clients (To get a list of all records paged...)
```url
{{host}}/clients?page=1&linesPerPage=6&direction=ASC&orderBy=id
```

### 2.  GET - (To get a client, By id)
```url
{{host}}/clients/1
```

### 3.  PUT - (To update client, By id)
```url
{{host}}/clients/1
```
```json
<!-- Body to requisition: -->
{
  "name": "John Brazilllll",
  "cpf": "12345678901",
  "income": 6500.0,
  "birthDate": "1994-07-20T10:30:00Z",
  "children": 2
}
```

This endpoint too can return a location to access resource

### 4.  DELETE - (To get a client, By id)
```url
{{host}}/clients/1
```

This endpoint remove a specific record of database list
