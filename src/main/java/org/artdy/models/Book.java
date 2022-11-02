package org.artdy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private int bookId;
    private String title;
    private String authorName;
    private int publicationYear;
    private int personId;
}
