package br.com.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.microservices.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
