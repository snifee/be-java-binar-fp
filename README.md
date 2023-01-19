# Final Project Synrgy Batch 5

https://be-java-binar-fp-staging.up.railway.app

## Registration

**POST /v1/auth/penyewa/register**
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
    email: string,
    password : string,
  }
```
* **Success Response:**  
  status: success  
  access_token:  `{JWT Token}`

* **Error Response:**  
  status: failed  
  message:  `{message failed}`
