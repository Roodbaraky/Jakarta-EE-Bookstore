package org.pluralsight;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Named
@Transactional(SUPPORTS)
@ApplicationScoped
public class BookService {
    @Inject
    EntityManager em;

    @Inject
    IsbnGenerator generator;

    public Book find(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER BY b.title DESC", Book.class
        );
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
        return query.getSingleResult();
    }

    @Transactional(REQUIRED)
    public Book create(Book book) {
        book.setIsbn(generator.generateNumber());
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)

    public void delete(Long id) {
        em.remove(em.getReference(Book.class, id));
    }

}
