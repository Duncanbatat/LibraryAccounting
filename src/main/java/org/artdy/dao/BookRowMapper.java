package org.artdy.dao;

import org.artdy.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("book_id"),
                rs.getInt("publication_year"),
                rs.getInt("person_id")
                );
        return book;
    }
}
