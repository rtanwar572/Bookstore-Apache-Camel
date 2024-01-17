package com.Rohit.service;

import com.Rohit.model.Book;
import com.Rohit.repo.IBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class BookService {
    @Autowired
    IBookRepo iBookRepo;

    //get list mapping
    public List<Book> getAllBooks() {
        return iBookRepo.findAll();
    }

    //get mapping
    public Book getBookById(Integer id) {
        return iBookRepo.findById(id).orElse(null);
    }

//post mapping
    public String addBook(Book book) {
        iBookRepo.save(book);
        return "201 : Book added successfully";
    }
    //delete mapping
    public String deleteBook(Integer id) {
        if(iBookRepo.isBookExistById(id)){
            iBookRepo.deleteById(id);
            return "200 : Book Deleted successfully !!";
            // Handle the case where the book with the given ID is not found
        }
        return "404 : Book Not Found !!";

    }
    //update mapping
    public String updateBookById(Integer bookId, Book updatedBook) {

        if (updatedBook.getBookId().equals(bookId)) {
            iBookRepo.save(updatedBook);
            return "200 : Book updated successfully";
        } else {
            // Handle the case where the book with the given ID is not found
            // You can throw an exception or return an error response
            return "Book not found";
        }

    }

}