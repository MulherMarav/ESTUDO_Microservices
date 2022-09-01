package br.com.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.microservices.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long> {

	Cambio findByFromAndTo(String from, String to);
}
