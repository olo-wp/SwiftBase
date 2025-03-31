Swift Code RestAPI 
----
This is project implements endpoints for querying banks by their swift codes and their ISO2 country codes. Endpoints are accessible at localhost:8080

### How to run ?
Requirements: Maven, Docker

to run the app together with tests:
```
mvn clean package
docker compose up -d
```

If you wish to run without testing:

```
mvn clean package -Dskiptests
docker compose up -d
```

### Endpoints

1. GET: /v1/swift-codes/{swift-code}
   - Retrieves information about branch or headquarter
   - Branch response: Contains information about the queried bank
   - Headquarter response: Contains information about the queried bank and its branches.
   
   
2. GET: /v1/swift-codes/country/{countryISO2code}
   - Return all SWIFT codes with details for a specific country (both headquarters and branches).

3. POST:  /v1/swift-codes
    - adds a new bank into the database
    - Request Structure: 
    ```
    {
    "address": string,
    "bankName": string,
    "countryISO2": string,
    "countryName": string,
    “isHeadquarter”: bool,
    "swiftCode": string,
    }
    ```

4. DELETE: /v1/swift-codes/{swift-code}
    - deletes a bank from the database.




   


