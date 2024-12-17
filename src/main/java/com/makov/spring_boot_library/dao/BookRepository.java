package com.makov.spring_boot_library.dao;
import com.makov.spring_boot_library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;


public interface BookRepository extends JpaRepository<Book, Long> {
        Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

        Page<Book> findByCategory(@RequestParam("category") String category , Pageable pageable);

        Page<Book> findByAuthorContaining(@RequestParam("author") String author, Pageable pageable);

        Page<Book> findByTitleContainingOrAuthorContaining(@RequestParam("title") String title,
                                                           @RequestParam("author") String author,
                                                           Pageable pageable);


}
