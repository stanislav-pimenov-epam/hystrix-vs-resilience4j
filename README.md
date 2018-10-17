# hystrix-vs-resilience4j



Feature | Hystrix | Resilience4j
------- | ------- | ------------
Bulkhead pattern implementation | ? | Y
Integration with Spring Boot 2 | ? | Y
Integration with WebFlux (Reactor) | integrated with JavaRX | Y
Prometheus metrics | ? | N ( meaningful metrics exposed just for CircuitBreaker) 
Configure bulkhead within application.yml from the box | ? | N (applicable for Ratelimiter and CircuitBreaker only)
Amount of transitive dependencies | ? | low

# Resilience4j

## Test Plan

![](img/test-plan.png)

## Bulkhead
### Example: max 25 concurrent request

![](img/bulkhead-25concurrent-calls.png)

### Exception count metrics
```
http_server_requests_seconds_count{exception="BulkheadFullException",method="GET",status="500",uri="/hello/{api}",} 645.0
http_server_requests_seconds_sum{exception="BulkheadFullException",method="GET",status="500",uri="/hello/{api}",} 0.457094154
http_server_requests_seconds_max{exception="BulkheadFullException",method="GET",status="500",uri="/hello/{api}",} 0.0
```

## RateLimiter
### Example 10 requests per seconds

![](img/ratemimiter-10rps.png)

### Exception count metrics
```
http_server_requests_seconds_count{exception="RequestNotPermitted",method="GET",status="500",uri="/hello/{api}",} 418.0
http_server_requests_seconds_sum{exception="RequestNotPermitted",method="GET",status="500",uri="/hello/{api}",} 838.681654162
http_server_requests_seconds_max{exception="RequestNotPermitted",method="GET",status="500",uri="/hello/{api}",} 2.065762051
```
