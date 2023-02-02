# Final Project Synrgy Batch 5

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
https://be-java-binar-fp-staging.up.railway.app <br>
https://kosthub-server.cleverapps.io/

## Registration

**POST /v1/auth/pencari/register**
=======
https://be-java-binar-fp-staging.up.railway.app

## Registration

**POST /v1/auth/penyewa/register**
>>>>>>> develop/staging
----
  Creates a new User, send email otp and returns the success message.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    "email": string,
    "phone": string,
    "password" : string,
  }
```
* **Success Response:**  
<<<<<<< HEAD
````
  {
    "status": "string",
    "message": "string",
    "data": {
      "access_token": "string, jwt",
      "user_details": {
        "id": "number",
        "fullname": "string",
        "email": "string",
        "phone": "string",
        "birthdate": "string",
        "gender": "string",
        "occupation": "string",
        "role": {
          "id": "number",
          "name": "string"
        },
        "verified": "boolean"
      }
    }
  }
````

* **Error Response:**  
````
  "status": BAD_REQUEST  
  "message":  `{message failed}`
  "error?":[
    {
      "field":"string",
      "message":"string"
    }
   ]
````
  
**POST /v1/auth/pemilik/register**
----
  Creates a new User, send email otp and returns the success message.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    "email": string,
    "phone": string,
    "password" : string,
  }
```
* **Success Response:**  
````
  {
    "status": "string",
    "message": "string",
    "data": {
      "access_token": "string, jwt",
      "user_details": {
        "id": "number",
        "fullname": "string",
        "email": "string",
        "phone": "string",
        "birthdate": "string",
        "gender": "string",
        "occupation": "string",
        "role": {
          "id": "number",
          "name": "string"
        },
        "verified": "boolean"
      }
    }
  }
````
* **Error Response:**  
````
  "status": BAD_REQUEST  
  "message":  `{message failed}`
  "error?":[
    {
      "field":"string",
      "message":"string"
    }
   ]
  
````
  
## Confirmation OTP (DEPRECATED)

**POST /v1/auth/confirm**
----
  Require email & password then returning jwt token.
* **URL Params**  
  otp : number
* **Headers**  
  Content-Type: application/json  
* **Body**  
```

```
* **Success Response:**  
  status: success  
  access_token:  `{message success}`

* **Error Response:**  
  status: failed  
=======
  status: success  
  message:  `{message success} `

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
  
**POST /v1/auth/penyedia/register**
----
  Creates a new User, send email otp and returns the success message.
=======
https://be-java-binar-fp-staging.up.railway.app

## Registration

**POST /v1/auth/penyewa/register**
----
  Creates a new User, send email otp and returns the success message.
>>>>>>> feature/booking
=======
https://be-java-binar-fp-staging.up.railway.app

## Registration

**POST /v1/auth/penyewa/register**
----
  Creates a new User, send email otp and returns the success message.
>>>>>>> feature/booking
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    email: string,
    phone: string,
    password : string,
  }
```
* **Success Response:**  
  status: success  
<<<<<<< HEAD
<<<<<<< HEAD
  message:  `{message success}`
=======
  message:  `{message success} `
>>>>>>> feature/booking

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
<<<<<<< HEAD
=======
  
**POST /v1/auth/penyedia/register**
----
  Creates a new User, send email otp and returns the success message.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    email: string,
    phone: string,
    password : string,
  }
```
* **Success Response:**  
  status: success  
  message:  `{message success}`

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
  
## Confirmation OTP

**POST /v1/auth/confirm**
----
  Require email & password then returning jwt token.
* **URL Params**  
  otp : number
* **Headers**  
  Content-Type: application/json  
* **Body**  
```

```
* **Success Response:**  
  status: success  
  access_token:  `{message success}`

* **Error Response:**  
  status: failed  
  message:  `{message failed}`  
>>>>>>> feature/booking
  
## Confirmation OTP

**POST /v1/auth/confirm**
----
  Require email & password then returning jwt token.
* **URL Params**  
  otp : number
* **Headers**  
  Content-Type: application/json  
* **Body**  
```

```
* **Success Response:**  
  status: success  
  access_token:  `{message success}`

* **Error Response:**  
  status: failed  
>>>>>>> develop/staging
=======
  message:  `{message success} `

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
  
**POST /v1/auth/penyedia/register**
----
  Creates a new User, send email otp and returns the success message.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    email: string,
    phone: string,
    password : string,
  }
```
* **Success Response:**  
  status: success  
<<<<<<< HEAD
  message:  `{message success}`

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
  
## Confirmation OTP

**POST /v1/auth/confirm**
----
  Require email & password then returning jwt token.
* **URL Params**  
  otp : number
* **Headers**  
  Content-Type: application/json  
* **Body**  
```

```
* **Success Response:**  
  status: success  
  access_token:  `{message success}`

* **Error Response:**  
  status: failed  
>>>>>>> feature/booking
  message:  `{message failed}`  
  
## Login

**POST /v1/auth/login**
----
  Require email & password then returning jwt token.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    "email": string,
    "password" : string,
  }
```
* **Success Response:**  
<<<<<<< HEAD
<<<<<<< HEAD
````
{
  "status": "string",
  "message": "string",
  "data": {
    "access_token": "string, jwt",
    "user_details": {
      "id": "number",
      "fullname": "string",
      "email": "string",
      "phone": "string",
      "birthdate": "string",
      "gender": "string",
      "occupation": "string",
      "role": {
        "id": "number",
        "name": "string"
      },
      "verified": "boolean"
    }
  }
}
````
* **Error Response:** 
````
  "status": UNAUTHORIZED,BAD_REQUEST  
  "message":  `{message failed}`
  "error?":[
    {
      "field":"string",
      "message":"string"
    }
   ]
  
````
=======
=======
>>>>>>> feature/booking
  status: success  
=======
>>>>>>> feature/booking
  access_token:  `{JWT Token}`

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop/staging
=======
>>>>>>> feature/booking
=======
>>>>>>> feature/booking
