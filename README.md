# Final Project Synrgy Batch 5
---
This branch was made only for test, using dummy data from mockapi.io

This request would show some information for each kost that have been registered, like kost name, price, facility, location, etc.

## How to use

### Kost

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
            "id": 10,
            "kostName": "McCullough LLC",
            "createdDate": "2023-01-07T18:32:49.143Z",
            "image": "https://loremflickr.com/640/480/city",
            "price": 825000,
            "capacity": 51,
            "facility": "facility 10",
            "rating": 2.35,
            "location": "Gulfport",
            "type": {
                "id": "2",
                "name": "Khsus Perempuan"
            },
            "categories": {
                "id": "2",
                "name": "Eksklusif"
            }
        }
],
    "error": null
}
```
