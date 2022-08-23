package br.com.microservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("book-service")
public class BugTestController {
	
	private Logger logger = LoggerFactory.getLogger(BugTestController.class);

	@GetMapping("/bug-test")
	@Retry(name = "bug-test", fallbackMethod = "fallbackMethod") //ele tentará a request 3 vezes por padrão, e na 3 apresenta o erro
	public String bugTest() { //o fallback serve para responder em caso de erro
		logger.info("Request to bug-test is received!");
		
		var response = new RestTemplate().getForEntity("http://localhost:8080/bug-test", String.class); //endereço não existe
		
		return response.getBody();
	}
	
	@GetMapping("/bug-test2")
	@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")//mecanismo de segurança que parece um disjuntor, CLOSED, OPEN e HALF_OPEN
	public String bugTest2() { //o fallback serve para responder em caso de erro
		logger.info("Request to bug-test is received!");
		
		var response = new RestTemplate().getForEntity("http://localhost:8080/bug-test", String.class); //endereço não existe
		
		return response.getBody();
	} //poweshell -> while(1) {curl http://localhost:8765/book-service/bug-test2; sleep 0.1}
	
	@GetMapping("/bug-test3")
	@RateLimiter(name = "default") //determina a quantidade de chamadas que pode fazer para um endpoint
	public String bugTest3() { 
		logger.info("Request to bug-test is received!");
				
		return "Test 3";
	} 
	
	@GetMapping("/bug-test4")
	@Bulkhead(name = "default") //determina a quantidade de execuções simultâneas/paralelas
	public String bugTest4() { 
		logger.info("Request to bug-test is received!");
				
		return "Test 4";
	} 
	
	public String fallbackMethod(Exception ex) { //pode fazer sobrecarga com diferentes exceptions e o mesmo metodo
		return "fallbackMethod Bug-Test";
	}
}
