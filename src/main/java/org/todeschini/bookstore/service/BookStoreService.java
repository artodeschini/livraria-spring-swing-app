package org.todeschini.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.todeschini.bookstore.model.Livro;
import org.todeschini.bookstore.repository.LivroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookStoreService implements IBookStoreService {

    @Autowired
    private LivroRepository repository;


    @Override
    public List<Livro> listarLivros() {
        return repository.findAll();
    }

    @Override
    public Optional<Livro> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Livro saveLivro(Livro l) {
        return repository.save(l);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
