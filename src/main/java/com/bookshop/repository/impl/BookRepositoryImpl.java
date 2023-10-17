package com.bookshop.repository.impl;

import com.bookshop.exception.DataProcessingException;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.model.Book;
import com.bookshop.repository.BookRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        try {
            sessionFactory.inTransaction(sessionFactory -> sessionFactory.persist(book));
            return book;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save book to the DB, book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get all books from DB", e);
        }
    }
}
