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
    public String show(@PathVariable("id") int bookId, Model model) {
        Book book = bookDao.show(bookId).get();
        Person person = book.isBorrowed() ? personDao.show(book.getPersonId()).get() : null;
        model.addAttribute("book", book);
        model.addAttribute("person", person);
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookDao.show(bookId).get());
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int bookId, @ModelAttribute("book") Book book) {
        bookDao.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookDao.delete(bookId);
        return "redirect:/books";
    }
}
