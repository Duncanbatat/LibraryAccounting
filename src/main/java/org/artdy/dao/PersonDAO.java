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
@SuppressWarnings({"unused", "deprecation"})
public class PersonDAO {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Person.class, id);
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO Person(full_name, date_of_birth) VALUES (?, ?)",
                person.getFullName(),
                person.getDateOfBirth()
        );
    }

    public void update(int id, Person person) {
        jdbcTemplate.update(
                "UPDATE Person SET full_name=?, date_of_birth=? WHERE id=?",
                person.getFullName(),
                person.getDateOfBirth(),
                id
        );
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM Person JOIN Book ON Person.id = Book.person_id WHERE Person.id=? ORDER BY title",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    public void delete(int id) {
        jdbcTemplate.update(
                "DELETE FROM Person WHERE id=?",
                id
        );
    }
}
