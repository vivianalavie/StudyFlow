package com.studyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.*;


// Just an example test case to try out spring
@SpringBootApplication
@RestController
@RequestMapping("/api/book")
public class SpringDemo {

    private Map<Long, Book> fakeRepo = new HashMap<>();

    public SpringDemo() {
        // Seed some fake data
        fakeRepo.put(1L, new Book(1L, "Test Book"));
        fakeRepo.put(2L, new Book(2L, "Another Book"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDemo.class, args);
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable long id) {
        Book book = fakeRepo.get(id);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @GetMapping("/")
    public Collection<Book> findBooks() {
        return fakeRepo.values();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable("id") final Long id, @RequestBody final Book book) {
        book.setId(id);
        fakeRepo.put(id, book);
        return book;
    }

    // Simple Book class
    static class Book {
        private Long id;
        private String title;

        public Book() {}

        public Book(Long id, String title) {
            this.id = id;
            this.title = title;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }

    // Simple Exception
    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class BookNotFoundException extends RuntimeException {}
}
