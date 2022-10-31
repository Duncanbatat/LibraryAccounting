package org.artdy.dao;

import org.artdy.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{person_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birthdate) VALUES (?, ?)",
                person.getName(), person.getBirthdate());
    }

    public void update(int person_id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, birthdate=? WHERE person_id=?",
                person.getName(), person.getBirthdate(), person_id);
    }

    public void delete(int person_id) {
        jdbcTemplate.update("DELETE * FROM Person WHERE person_id=?",
                person_id);
    }
}
