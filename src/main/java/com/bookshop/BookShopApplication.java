package com.bookshop;

import com.bookshop.model.Book;
import com.bookshop.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            Book book1 = new Book();
            book1.setAuthor("Steve Krug");
            book1.setTitle("Don't make me think");
            book1.setIsbn("9785699914920");
            book1.setPrice(BigDecimal.valueOf(579));
            bookService.save(book1);
            System.out.println(bookService.findAll());
        };
    }
}
