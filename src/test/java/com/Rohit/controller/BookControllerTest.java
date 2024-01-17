package com.Rohit.controller;

import com.Rohit.model.Book;
import com.Rohit.service.BookService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    Book book1;
    Book book2;

    ArrayList<Book> books=new ArrayList<>();

    @BeforeEach
    void setUp() {
        book1=new Book(1,"moye moye","yehh","jojo",2022);
        book2=new Book(2,"mitii de tibiyan","sidhu","Sudhu Moose wala",2021);
        books.add(book1);
        books.add(book2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(books);
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetBook() throws Exception {
        when(bookService.getBookById(1)).thenReturn(book1);
        this.mockMvc.perform(get("/books/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testPostBook() throws Exception {
        String json = new Gson().toJson(book1);
        when(bookService.addBook(book1)).thenReturn("201 : Book added successfully");
        this.mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isOk());
    }


    @Test
    void testUpBooks() throws Exception {
        book1.setBookName("Kritika");
        String json = new Gson().toJson(book1);
        when(bookService.updateBookById(1,book1)).thenReturn("200 : Book updated successfully");
        this.mockMvc.perform(put("/books/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testDeleteABook() throws Exception {
        when(bookService.deleteBook(1)).thenReturn("200 : Book Deleted successfully !!");
        this.mockMvc.perform(delete("/books/1")).andDo(print()).andExpect(status().isOk());
    }
}