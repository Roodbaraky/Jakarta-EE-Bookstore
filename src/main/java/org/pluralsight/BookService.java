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
//Specify the transaction policy
//Transactions are a bit like a promise.all, they ensure all operations complete, or none of them do.
//SUPPORTS - if a transaction is active, JTA will use it. If not, it will run without a transaction.
//REQUIRED - if a transaction is active, JTA will use it. If not, it will create a new one.
//REQUIRED is the implicit default, we only use it below to override the SUPPORTS policy we set for the class
//Supports is fine for some of these methods, as they are effectively read-only, and don't make critical make or break changes to the database.
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
