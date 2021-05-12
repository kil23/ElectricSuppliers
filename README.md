# ElectricSuppliers

Build this project using
mvn clean install -U

Run this project using
mvn jetty:run

Open Postman and use these payload to make call to APIs 
1) Insert Data from CSV 
curl --location --request POST 'http://localhost:9001/sup/insert'

2) Get Data 
curl --location --request GET 'http://localhost:9001/sup/02451'

3) Cheapest Plan
curl --location --request POST 'http://localhost:9001/sup/cheapest-plan' \
--header 'Content-Type: application/json' \
--data-raw '{
    "zipCode": "02451",
    "duration": "6"
}'

4) Top 3 Cheaper plans
curl --location --request POST 'http://localhost:9001/sup/top-three-cheaper-plan' \
--header 'Content-Type: application/json' \
--data-raw '{
    "zipCode": "02451",
    "duration": "36"
}'

5) Greenest Plan
curl --location --request POST 'http://localhost:9001/sup/greenest-plan' \
--header 'Content-Type: application/json' \
--data-raw '{
    "zipCode": "02451",
    "duration": "6"
}'

6) Top 3 Greener plans
curl --location --request POST 'http://localhost:9001/sup/top-three-greener-plan' \
--header 'Content-Type: application/json' \
--data-raw '{
    "zipCode": "02451",
    "duration": "36"
}'
