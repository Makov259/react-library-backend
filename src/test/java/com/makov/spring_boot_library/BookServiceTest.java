package com.makov.spring_boot_library;

import com.makov.spring_boot_library.dao.BookRepository;
import com.makov.spring_boot_library.dao.CheckoutRepository;
import com.makov.spring_boot_library.entity.Book;
import com.makov.spring_boot_library.entity.Checkout;
import com.makov.spring_boot_library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CheckoutRepository checkoutRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckoutBook_Success() throws Exception {
        // GIVEN (Setup test data)
        String userEmail = "testuser@gmail.com";
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setCopiesAvailable(5);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(checkoutRepository.findByUserEmailAndBookId(userEmail, bookId)).thenReturn(null);

        // WHEN (Calling the method)
        Book checkedOutBook = bookService.checkoutBook(userEmail, bookId);

        // THEN (Assertions)
        assertNotNull(checkedOutBook);
        assertEquals(4, checkedOutBook.getCopiesAvailable());
        verify(bookRepository, times(1)).save(book);
        verify(checkoutRepository, times(1)).save(any(Checkout.class));
    }

    @Test
    void testCheckoutBook_AlreadyCheckedOut() {
        String userEmail = "testuser@gmail.com";
        Long bookId = 1L;

        when(checkoutRepository.findByUserEmailAndBookId(userEmail, bookId)).thenReturn(new Checkout());

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.checkoutBook(userEmail, bookId);
        });

        assertEquals("Book does not exist or already checked out by user.", exception.getMessage());
    }

    @Test
    void testCheckoutBook_NoCopiesAvailable() {
        String userEmail = "testuser@gmail.com";
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setCopiesAvailable(0);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.checkoutBook(userEmail, bookId);
        });

        assertEquals("Book does not exist or already checked out by user.", exception.getMessage());
    }
}
