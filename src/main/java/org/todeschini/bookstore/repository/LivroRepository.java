package org.todeschini.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todeschini.bookstore.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
