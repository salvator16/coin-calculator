# Scenario

Every 10 seconds last price for BTC based on EUR/USD fetched and stored hazelcast instance single node only one thread (RefresherService)
 
Price calculation and conversion via Async api fetch data from cache map.

Exception handler and custom exceptions via Spring AOP 

For async api Completable Future Api preferred

Only integration test added ** further will be added

Somehow btc api can not be accessible or single hzm instance shuttown, cache and price list will be released with 30 second based on TTL

### Docker build for test 

docker build -t coin/coin-calculator .

docker start -p 8080:8080 coin/coin-calculator

### Swagger ui can be accessible via this link
(if containerized change localhost with docker subnet ip)

http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/

Sample Request/Response for BTC Amount to Real Price

curl -X GET "http://localhost:8080/api/v1/coin-api/EUR/coin-type/0.000123/BTC" -H  "accept: application/json"

{
"modifiedDate": "2021-03-16T23:13:48.520872",
"amount": 5.8555134,
"type": "EUR",
"coinAmount": 0.000123,
"coinType": "BTC"
}

Sample Request/Response for  Real Price to BTC Amount

curl -X GET "http://localhost:8080/api/v1/coin-api/256/USD" -H  "accept: application/json"

{
"modifiedDate": "2021-03-16T23:15:08.216943",
"amount": 256,
"type": "USD",
"coinAmount": 0.00430247,
"coinType": "BTC"
}

### Tech spec for sample purposes
Spring boot
Hazelcast 4.2
Junit
Docker
