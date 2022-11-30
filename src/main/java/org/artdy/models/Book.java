package org.artdy.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "book")
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым.")
    @Size(min = 2 , max = 100, message = "Название книги должно быть длиной от 2 до 100 символов.")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Имя автора не должно быть пустым.")
    @Size(min = 2 , max = 100, message = "Имя автора должно быть длиной от 2 до 100 символов.")
    @Column(name = "author")
    private String author;

    @PositiveOrZero(message = "Год публикации книги может быть только положительным числом.")
    @Column(name = "year")
    private int year;

    @Transient
    private boolean expired;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public String getFormattedTakenAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(takenAt);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
