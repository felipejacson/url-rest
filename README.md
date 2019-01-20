# url-rest

To run this project use:

mvn spring-boot:run

This application uses PostgreSQL:

url=jdbc:postgresql://localhost:5432/url
username=postgres
password=postgres

This application is a microservice and can be distributed in many server using Eureka.
The commands are:

/api/ (GET): return all registers.

/api/{id} (GET): return a specific register by id.

/api/n/{name} (GET): return a specific register by friendly name.

/api/find (POST): return a list of registers using a JSON:

{
    "id": 1,
    "url": "http://google.com/",
    "friendlyName": "Google"
}

/api/ (POST): send a register to the server:

{
    "url": "http://google.com/",
    "friendlyName": "Google"
}

/api/{id} (PUT): update a register.

{
    "url": "http://google.com/",
    "friendlyName": "Google"
}

/api/{id} (DELETE): delete a register.

THIS APPLICATION MUST BE USED WITH THIS PROJECT:

https://github.com/felipejacson/url
