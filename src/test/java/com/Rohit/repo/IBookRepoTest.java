package com.Rohit.repo;

import com.Rohit.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@DataJpaTest
@SpringBootTest
class IBookRepoTest {
    @Autowired
    IBookRepo iBookRepo;

    @BeforeEach
    void setUp() {
        Book book=new Book(2,"hey hey","jojo","honey singh",2021);
        iBookRepo.save(book);
    }
    @AfterEach
    void tearDown() {
        System.out.println("Tearing down ....");
        iBookRepo.deleteAll();
    }
//success case
    @Test
    void isBookExistById() {
        boolean actual=iBookRepo.isBookExistById(1);
        assertThat(actual).isTrue();

    }
//failure case
    @Test
    void isBookNotExistById() {
        boolean actual=iBookRepo.isBookExistById(99);
        assertThat(actual).isFalse();
    }

}