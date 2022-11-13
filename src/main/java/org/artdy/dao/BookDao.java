package org.artdy.dao;

import org.artdy.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("deprecation")
@Component
public class BookDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query(
                "SELECT * FROM Book",
                new BookRowMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update(
                "INSERT INTO Book(title, author_name, publication_year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthorName(),
                book.getPublicationYear()
        );
    }

    public Optional<Book> show(int bookId) {
        return jdbcTemplate.query(
                "SELECT * FROM Book WHERE book_id=?",
                new Object[]{bookId},
                new BookRowMapper()).stream().findAny();
    }

    public void update(int bookId, Book book) {
         jdbcTemplate.update(
                "UPDATE Book SET title=?, author_name=?, publication_year=? WHERE book_id=?",
                book.getTitle(),
                book.getAuthorName(),
                book.getPublicationYear(),
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
