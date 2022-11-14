package org.artdy.models;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
}
