package org.artdy.models;

public class Person {
    private int person_id;
    private String name;
    private int birthdate;

    public Person(int person_id, String name, int birthdate) {
        this.person_id = person_id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Person() {
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }
}
