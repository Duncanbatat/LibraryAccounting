package org.artdy.services;

import org.artdy.models.Book;
import org.artdy.models.Person;
import org.artdy.repositories.BookRepository;
import org.artdy.repositories.PeopleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll(Sort.by("year"));
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void release(int id){
        bookRepository.findById(id).ifPresent(book -> book.setOwner(null));
    }

    @Transactional
    public void assign(int id, int newOwnerId) {
        Book bookToBeUpdated = bookRepository.getOne(id);
        Person newOwner = peopleRepository.getOne(newOwnerId);
        bookToBeUpdated.setOwner(newOwner);
        bookToBeUpdated.setTakenAt(new Date());
    }
}
