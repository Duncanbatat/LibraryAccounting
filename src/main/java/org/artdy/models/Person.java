package org.artdy.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "person")
@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
public class Person {
    @Transient
    private final String DATE_FORMAT = "dd.MM.yyyy";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date dateOfBirth;

    //TODO Сделать ленивую подгрузку
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public String getFormattedDateOfBirth() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(dateOfBirth);
    }

    public String getYearOfBirth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(dateOfBirth);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", yearOfBirth=" + dateOfBirth +
                '}';
    }
}
