package org.todeschini.bookstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todeschini.bookstore.exception.ResourceNotFindException;
import org.todeschini.bookstore.model.Livro;
import org.todeschini.bookstore.service.IBookStoreService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("book-store")
public class BookStoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreController.class);

    @Autowired
    private IBookStoreService service;

    @GetMapping("/livros")
    public List<Livro> listAll() {
        List<Livro> produtos = service.listarLivros();
        produtos.forEach(b -> LOGGER.info(b.toString()));
        return produtos;
    }

    @PostMapping("/livros")
    public ResponseEntity<Livro> salvarProduto(@RequestBody Livro l) {
        return new ResponseEntity<Livro>(service.saveLivro(l), HttpStatus.CREATED);
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Integer id) {
        Optional<Livro> p = service.findById(id);

        if (p.isPresent()) {
            return ResponseEntity.ok(p.get());
        } else {
            throw new ResourceNotFindException("Nao se encontrou o Livro com id " + id);
        }
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<Livro> editarProduto(@PathVariable Integer id, @RequestBody Livro l) {
        Optional<Livro> database = service.findById(id);

        if (database.isPresent()) {
            Livro original = database.get();
            original.setNome(l.getNome());
            original.setAutor(l.getAutor());
            original.setPreco(l.getPreco());
            original.setQuantidade(l.getQuantidade());


            return ResponseEntity.ok(service.saveLivro(original));

        } else {
            throw new ResourceNotFindException("Nao se encontrou o livro com id " + id);
        }
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Map<String,Boolean>> delete(@PathVariable Integer id) {
        Optional<Livro> p = service.findById(id);

        if (p.isPresent()) {
            service.deleteById(id);

            Map<String,Boolean> resposta = new HashMap<>();
            resposta.put("delete", Boolean.TRUE);
            return ResponseEntity.ok(resposta);
        } else {
            throw new ResourceNotFindException("Nao se encontrou o produto com id " + id);
        }
    }

}
