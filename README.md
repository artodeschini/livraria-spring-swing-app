# Sistema de cadastro de livros Swing e Spring

## requisitos

* maven
* java 17

## spring

Esté sistema foi criaod utilizando [spring](https://start.spring.io/) com Java 17 
A versão do spring utilizada foi 3.1.4 com maven 
Para maiores detalhes e ajuda com spring com [spring](HELP.md)

## UI com Swing

A UI foi desenvolvido utilizando java Swing com integração com Spring que realiza a persistencia dos dados usando 
Spring Data e jpa

## Para detalhes da integracao Swing com Spring

Veja o a classe que implementa [Application](src/main/java/org/todeschini/bookstore/BookStoreApplication.java)
e a classe que implementa [JFrame Component Spring](src/main/java/org/todeschini/bookstore/form/LivroForm.java)

## Base de dados

Foi utilizada [h2 database](https://www.h2database.com/html/main.html), porém pode ser substituido por qualquer banco de dados relacional que tenha suporte a jdbc4
