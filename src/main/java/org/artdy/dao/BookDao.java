package org.artdy.dao;

import org.artdy.models.Book;
import org.artdy.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@SuppressWarnings("deprecation")
public class BookDao {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Transactional
    public void save(Book book) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(book);
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Book.class, id);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session currentSession = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = currentSession.get(Book.class, id);
        bookToBeUpdated.setTitle(updatedBook.getTitle());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setYear(updatedBook.getYear());
    }

    @Transactional(readOnly = true)
    public Person getBookOwner(int id) {
        return jdbcTemplate.query(
                "SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)
        ).stream().findAny().orElse(null);
    }

    public void assignOwner(int id, int personId) {
        jdbcTemplate.update(
                "UPDATE Book SET person_id=? WHERE id=?",
                personId,
                id
        );
    }

    public void releaseBook(int bookId) {
        jdbcTemplate.update(
                "UPDATE Book SET person_id = NULL WHERE id=?",
                bookId
        );
    }

    public void delete(int bookId) {
        jdbcTemplate.update(
                "DELETE FROM Book WHERE book_id=?",
                bookId
        );
    }
}
