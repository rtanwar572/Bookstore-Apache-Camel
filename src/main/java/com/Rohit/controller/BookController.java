package com.Rohit.controller;

import com.Rohit.model.Book;
import com.Rohit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public String postBook(@RequestBody Book book) {

        return bookService.addBook(book);
    }
    @PutMapping("/")
    public String upBooks(@RequestBody Book book){
        return bookService.updateBookById(book.getBookId(),book);
    }

    @DeleteMapping("/{id}")
    public String deleteABook(@PathVariable Integer id) {
        return bookService.deleteBook(id);
        }

}
