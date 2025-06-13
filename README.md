Customer Rewards Calculator
********************

This Spring boot Application Calculates monthly and total reward points. It uses simple reward logic and stores data in an in-memory sql database.

**********************


Project Structure

src
├── main
│   ├── java
│   │   └── com.example.restservice
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       └── mapper
│   │       ├── model
│   │       └── repository
│   │       └── services
│   └── resources
│       ├── application.properties
│       ├── data123.sql


*************************

How to Build and Run

mvn clean install

mvn spring-boot:run

or run RewardsApplication.java from your IDE

*********************************

Rest API usage

GET /api/rewards/customers


http://localhost:8080/api/rewards/customers


Example Response:

```json
[
  {
    "id": 1,
    "name": "Ram Krishna",
    "email": "ram.krishna@example.com",
    "transactions": [
      {
        "id": 1,
        "customerId": 1,
        "amount": 10.0,
        "date": "2025-06-13",
        "description": "Ram Krishna"
      }
    ]
  }
]
```


**************************
GET /api/rewards/calculate

http://localhost:8080/api/rewards/calculate

Example Response:

```json
[
  {
    "customerId": 1,
    "customerName": "Ram Krishna",
    "monthlyRewards": {
      "2025-06": 0
    },
    "totalRewards": 0
  }
]
```

*********************************************
Postman folder in the project directory has the postman collection for API testing







