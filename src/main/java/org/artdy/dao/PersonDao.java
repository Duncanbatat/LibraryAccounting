package org.artdy.dao;

import org.artdy.models.Book;
import org.artdy.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(
                "SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

    public Person show(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM Person WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)
        ).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO Person(full_name, year_of_birth) VALUES (?, ?)",
                person.getFullName(),
                person.getYearOfBirth()
        );
    }

    public void update(int id, Person person) {
        jdbcTemplate.update(
                "UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?",
                person.getFullName(),
                person.getYearOfBirth(),
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
