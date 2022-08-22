package br.com.microservices.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.microservices.model.Book;
import br.com.microservices.proxy.CambioProxy;
import br.com.microservices.repository.BookRepository;
import br.com.microservices.response.Cambio;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private Environment environment;

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy proxy;

	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

		var findBook = repository.findById(id);

		if (!findBook.isPresent())
			throw new RuntimeException("Book not found!");

		Book book = findBook.get();

		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		
		book.setPrice(cambio.getConvertedValue());
		book.setCurrency(currency);

		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port + " - feign");

		return book;
	}

	/*
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

		var findBook = repository.findById(id);

		if (!findBook.isPresent())
			throw new RuntimeException("Book not found!");

		Book book = findBook.get();

		HashMap<String, String> params = new HashMap<>();

		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		
		var response = new RestTemplate()
				.getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class,
				params);
		
		var cambio = response.getBody();
		
		book.setPrice(cambio.getConvertedValue());
		book.setCurrency(currency);

		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port);

		return book;
	} */
}
