package org.artdy.controllers;

import org.artdy.dao.PersonDao;
import org.artdy.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDao.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int person_id, Model model) {
        model.addAttribute("person", personDao.show(person_id).get());
        return "/people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "/people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") Person person) {
        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int person_id, Model model) {
        model.addAttribute("person", personDao.show(person_id).get());
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int person_id, @ModelAttribute("person") Person person) {
        personDao.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int person_id) {
        personDao.delete(person_id);
        return "redirect:/people";
    }
}
