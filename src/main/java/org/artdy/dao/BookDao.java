package org.artdy.dao;

import org.artdy.models.Book;
import org.artdy.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("deprecation")
public class BookDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query(
                "SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update(
                "INSERT INTO Book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear()
        );
    }

    public Book show(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM Book WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book book) {
         jdbcTemplate.update(
                "UPDATE Book SET title=?, author_name=?, publication_year=? WHERE id=?",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                id
        );
    }

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
