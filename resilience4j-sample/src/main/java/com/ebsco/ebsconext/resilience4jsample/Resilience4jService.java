package com.ebsco.ebsconext.resilience4jsample;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Resilience4jService {

  private CircuitBreaker circuitBreaker;
  //private TimeLimiter timeLimiter;
  //private ExecutorService executorService = Executors.newFixedThreadPool(10);
  private Function<String, String> chainedCallable;

  @Autowired
  private RestTemplate restTemplate;

  public Resilience4jService() {

//    // Create a custom configuration for a CircuitBreaker
//    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//        .failureRateThreshold(50)
//        .waitDurationInOpenState(Duration.ofMillis(10000))
//        .ringBufferSizeInHalfOpenState(2)
//        .ringBufferSizeInClosedState(4)
//        .enableAutomaticTransitionFromOpenToHalfOpen()
//        .build();
//    // Create a CircuitBreakerRegistry with a custom global configuration
    //CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.(circuitBreakerConfig);

    circuitBreaker = CircuitBreaker.of("apiCall", CircuitBreakerConfig.ofDefaults());
    chainedCallable = CircuitBreaker.decorateFunction(circuitBreaker, this::restrictedCall);
  }

  public String callApi(String api) {

    return chainedCallable.apply(api);
  }

  private String restrictedCall(String api) {
    return restTemplate.getForObject("http://localhost:8888/" + api, String.class);
  }
}
