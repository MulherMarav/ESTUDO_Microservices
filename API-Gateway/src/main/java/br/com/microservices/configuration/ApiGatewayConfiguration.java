package br.com.microservices.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean //configurações de rotas, como header param ou path param, autenticação, redirecionamento, gerenciar url
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
	/*	Function<PredicateSpec, Buildable<Route>> function = 
				p -> p.path("/get") //interceptar e redirecionar
						.filters(f -> f
								.addRequestHeader("Hello", "World")
								.addRequestParameter("Hello", "World"))
					   .uri("http://httpbin.org:80"); 
					   //ferramenta de diagnostico que converte chamadas HTTP em um JSON como response
				//status do serviço em JSON: http://localhost:8765/get
	*/		
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f
								.addRequestHeader("Hello", "World")
								.addRequestParameter("Hello", "World"))
					   .uri("http://httpbin.org:80")) //lambda function
				.route(p -> p.path("/cambio-service/**").uri("lb://cambio-service")) 
				.route(p -> p.path("/book-service/**").uri("lb://book-service")) 
				.build();
		
		//passa o nome do serviço registrado no eureka, quando a request chega no gateway, acessa
		// a eureka, e a eureka encontra a location e balancea as cargas entre as diferentes instancias
		
		//http://localhost:8765/book-service/5/BRL
		//http://localhost:8765/cambio-service/8/USD/BRL
	} 
	
}
