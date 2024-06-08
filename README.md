# Cinemille RESTAPI

Multiplex CMS RestAPI Java Spring-Boot based

## BEFORE STARTING

Configure the application.properties file with the relevant customized data (administrator data, database connection data).

Then, make a GET request to the endpoint https://hostname:port/api/auth/firstboot.


## ENDPOINTs

The endpoints are all documented using OpenAPI and Swagger.

You can consult them in JSON format by making a GET request to\
 https://hostname:port/api/docs or by visiting\
  https://hostname:port/api/swagger-ui.html in a web browser.

## AUTHENTICATION

All administrative endpoints are protected by JWT-based authentication.

The documentations are accessible without authentication; however, if used in production, they should be either disabled or filtered beforehand.

## USER INTERFACE

A frontend GUI is available, designed in conjunction with this API for the Lascaux Challenge.

Check out my GitHub --> https://github.com/maioranav/cinemillefe