# Final Project Synrgy Batch 5
---
This branch was made only for test, using dummy data from mockapi.io
This request would show some information for each kost that have been registered, like kost name, price, facility, location, etc.

## How to use

###Kost

**GET /mock-api/kost**

Return all information that user need, in this case I put 50 dummy data but for example I'll show 2.

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
{
[
    "status": 200,
    "message": "Succes",
    "data": [
        {
            "id": 1,
            "kostName": "Tremblay - Dach",
            "createdDate": "2023-01-07T22:14:33.289Z",
            "image": "https://loremflickr.com/640/480/city",
            "price": 545000,
            "capacity": 16,
            "facility": "facility 1",
            "rating": 0.71,
            "location": "Scottsdale",
            "type": {
                "id": "0",
                "name": "Campuran"
            },
            "categories": {
                "id": "0",
                "name": "Murah"
            }
        },
        {
            "id": 2,
            "kostName": "Connelly - Waelchi",
            "createdDate": "2023-01-07T13:13:48.629Z",
            "image": "https://loremflickr.com/640/480/city",
            "price": 956000,
            "capacity": 26,
            "facility": "facility 2",
            "rating": 0.7,
            "location": "San Marcos",
            "type": {
                "id": "0",
                "name": "Campuran"
            },
            "categories": {
                "id": "0",
                "name": "Murah"
            }
        }
],
    "error": null
}
```
