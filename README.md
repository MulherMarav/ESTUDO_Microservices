# Microservices
ANDAMENTO - Estudo sobre Microservices, com Spring Cloud.

</br>

# Conteúdo do Curso

* Spring Cloud Configuration (Archaius) e Spring Actuator
<p>Criando Config Server e Service Greeting.</p>
<p>Config server para guardar info do servidor como os application.yml, Cloud Server e Client.</p>

* Microservices com Feign
<p>Criando os Services Cambio e Book.</p>
<p>Dois services de exemplos com comunicação entre eles por Feign.</p>

* Eureka
<p>Criando o Service Registry/Discovery ou Naming Server.</p>
<p>Armazenar, localizar e distribuir serviços de URL, Port, Host e etc.</p>

* Load Balancer
<p>Criando com Eureka, Feign e Spring Cloud Load Balancer.</p> 
<p>Balanceamento de carga entre os services.</p>

* API Gateway
<p>Criando RouterLocator e Filters em Gateways.</p>
<p>Configuração das rotas dos services.</p>

* Resilience4j
<p>Criando Retry (Wait Duration e Exponencial Backoff), Fallback Methods, Circuit Breaker, Rate Limit e Bulkhead.</p> 
<p>Resilience4j é uma biblioteca leve de tolerância a falhas; quando um service está off ou com lentidão.</p>

* Loggar Distributed tracing
<p>Loggando as request dos services com Zipkin e Slueth.</p> 
<p>Rastreamento entre as requisições, para medir o tempo dos services para executar ações das request.</p>

Run via container do Docker
 
```
docker run -p 9411:9411 openzipkin/zipkin:2.23.2

```

![image](https://user-images.githubusercontent.com/101612046/186575412-e59a21fb-d157-46ce-881e-9d6f7f2cb83f.png)

</br>

# Eureka

![image](https://user-images.githubusercontent.com/101612046/186040152-445c96ef-14d9-415a-8d6c-342b1be1859b.png)

</br>

# Endpoint's dos Services

![image](https://user-images.githubusercontent.com/101612046/186040227-2dd6c586-0fad-4838-b0b7-53f18521b106.png)
![image](https://user-images.githubusercontent.com/101612046/186040264-46d17357-a69a-4828-829f-ea617cdccd05.png)



