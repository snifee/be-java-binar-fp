# Final Project Synrgy Batch 5

Server kost app

## Registration

**POST {url}/v1/auth/register**
----
  Returns all users in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Success Response:**  
* **Code:** 200  
  **Content:**  
```
**Body** <br />
{
    "email" : {email} <br />
    "phone" : {phone} <br />
    "password" : {password} <br />
}  
