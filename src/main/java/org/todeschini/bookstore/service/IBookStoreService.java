package org.todeschini.bookstore.service;

import org.todeschini.bookstore.model.Livro;

import java.util.List;
import java.util.Optional;

public interface IBookStoreService {

    public List<Livro> listarLivros();

    public Optional<Livro> findById(Integer id);

    public Livro saveLivro(Livro l);

    public void deleteById(Integer id);
}
