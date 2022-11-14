package org.artdy.controllers;

import org.artdy.dao.BookDao;
import org.artdy.dao.PersonDao;
import org.artdy.models.Book;
import org.artdy.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private  final PersonDao personDao;
    private final BookDao bookDao;

    @Autowired
    public BookController(PersonDao personDao, BookDao bookDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "/books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookDao.show(id);
        Person bookOwner = bookDao.getBookOwner(id);
        model.addAttribute("book", book);
        model.addAttribute("bookOwner", bookOwner);
        model.addAttribute("person", new Person());
        model.addAttribute("people", personDao.index());
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDao.assignOwner(id, person.getId());
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
