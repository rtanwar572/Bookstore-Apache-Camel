package com.Rohit.model;

import com.Rohit.repo.IBookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class BookTest {
    @Autowired
    private IBookRepo iBookRepo;

    @Test
    void testBookEntity() {
        // Given
        Book book = new Book(null, "Sample Book", "Sample Title", "Sample Author", 2022);

        // When
        Book savedBook = iBookRepo.save(book);
        Book retrievedBook = iBookRepo.findById(savedBook.getBookId()).orElse(null);

        // Then
        assertNotNull(retrievedBook);
        assertEquals("Sample Book", retrievedBook.getBookName());
        assertEquals("Sample Title", retrievedBook.getBooTitle());
        assertEquals("Sample Author", retrievedBook.getBookAuth());
        assertEquals(2022, retrievedBook.getPublishYear());
    }
}
