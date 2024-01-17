package com.Rohit.service;

import com.Rohit.model.Book;
import com.Rohit.repo.IBookRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Autowired
    BookService bookService;
    @Autowired
    IBookRepo iBookRepo;
    Book book;

    @BeforeEach
    void setUp() {
         book=new Book(1,"Yoyo","get stet","JoJo Aukha",2024);

    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing Down .....");

    }

    @Test
    void testGetAllBooks() {
       Integer actual=iBookRepo.findAll().size();
       Integer expect=bookService.getAllBooks().size();
       assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testGetBookById() {
        Book actual=iBookRepo.findById(1).orElse(null);
        Book expected=bookService.getBookById(1);
        assertThat(actual).isEqualTo(expected);
    }
//failure test case
    @Test
void testFailGetBookById() {
    Book actual=iBookRepo.findById(99).orElse(null);
    Book expected=bookService.getBookById(99);
    assertThat(actual).isEqualTo(expected);
}
    @Test
    void testAddBook() {

        String actual = bookService.addBook(book);
        assertThat(actual).isEqualTo("201 : Book added successfully");
    }
    @Test
    void testUpdateBookById() {
        // Create an updated book with the same ID
//        iBookRepo.save(book);
        book.setBookName("Chohko");

        // Call the updateBookById method
        String result = bookService.updateBookById(book.getBookId(), book);

        // Assert the result
        assertEquals("200 : Book updated successfully", result);
//
//        assertTrue(iBookRepo.existsById(1));
//         Optionally, assert that the book in the repository is updated with the new data
//        Book retrievedBook = iBookRepo.findById(2).orElse(null);
//        assertNotNull(retrievedBook);

    }

    @Test
    public void testUpdateBookByIdNotFound() {
        // Assuming there is no book with ID 999 in the repository
//        Book updatedBook = new Book();
        book.setBookName("Honey Honey");
        // Call the updateBookById method with a non-existing ID
        String result = bookService.updateBookById(999, book);
        // Assert the result
        assertEquals("Book not found", result);
    }
//    @Ignore
//    @Disabled
    @Test
    void testDeleteBook() {
        iBookRepo.save(book);
        // Call the deleteBook method
        String result = bookService.deleteBook(book.getBookId());

        // Assert the result
        assertEquals("200 : Book Deleted successfully !!", result);

        // Optionally, assert that the book with ID 1 does not exist in the repository anymore
//        assertFalse(iBookRepo.existsById(1));
    }
    @Test
    public void testDeleteNonExistingBook() {
        // Call the deleteBook method with an ID that doesn't exist
        String result = bookService.deleteBook(999);

        // Assert the result
        assertEquals("404 : Book Not Found !!", result);
    }

}