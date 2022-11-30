package org.artdy.util;

import org.artdy.models.Person;
import org.artdy.services.PeopleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValidator implements Validator {
    private final PeopleService peopleService;

    public PeopleValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleService.getPersonByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Человек с таким именем уже существует.");
        }
    }
}
