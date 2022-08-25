package br.com.microservices.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.model.Cambio;
import br.com.microservices.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("cambio-service")
@Tag(name = "Cambio")
public class CambioController {

	Logger logger = LoggerFactory.getLogger(CambioController.class);
	
	@Autowired
	private Environment environment;

	@Autowired
	private CambioRepository repository;

	@GetMapping("/{amount}/{from}/{to}")
	@Operation(summary = "Find a specific cambio.")
	public Cambio getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from,
			@PathVariable("to") String to) {

		logger.info("getCambio is called with -> {}, {} and {}", amount, from, to);
		
		var cambio = repository.findByFromAndTo(from, to);

		if (cambio == null) throw new RuntimeException("Currency Unsupported");

		var port = environment.getProperty("local.server.port");
		cambio.setEnvironment(port);

		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);

		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING)); // arredonda para 2 casas decimais

		return cambio;
	}
}
