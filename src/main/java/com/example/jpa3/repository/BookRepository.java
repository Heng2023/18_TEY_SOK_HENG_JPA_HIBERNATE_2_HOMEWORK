package com.example.jpa3.repository;

import com.example.jpa3.model.Book;
import com.example.jpa3.model.requestbody.BookRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Book createBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        entityManager.persist(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM books b", Book.class).getResultList();
    }

    public Book getBookById(UUID id) {
        return entityManager.find(Book.class, id);
    }

    public List<Book> getBooksByTitle(String title) {
        return entityManager.createQuery("SELECT b FROM books b WHERE LOWER(b.title) LIKE LOWER(:title)", Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    @Transactional
    public Book updateBookById(UUID id, BookRequest bookRequest) {
        Book book = getBookById(id);
        if (book != null) {
            entityManager.detach(book);
            modelMapper.map(bookRequest, book);
            book = entityManager.merge(book);
        }
        return book;
    }

    @Transactional
    public boolean deleteBookById(UUID id) {
        Book book = getBookById(id);
        if (book != null) {
            entityManager.remove(book);
            return true;
        }
        return false;
    }
}
