package com.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookshop.dto.book.BookDto;
import com.bookshop.dto.book.CreateBookRequestDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.BookMapper;
import com.bookshop.mapper.impl.BookMapperImpl;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Spy
    private BookMapper bookMapper = new BookMapperImpl();

    @Test
    @DisplayName("Create a book with valid data")
    public void save_WithValidData_ShouldReturnBookDto() {
        Long id = 1L;

        CreateBookRequestDto requestDto = new CreateBookRequestDto("Turkey Trouble",
                "Wendi Silvano",
                "9780761455296",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                List.of(id));

        Book book = new Book();
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());
        book.setPrice(requestDto.price());
        book.setDescription(requestDto.description());
        book.setCoverImage(requestDto.coverImage());
        book.setCategories(Set.of(new Category(id)));

        when(bookMapper.toBook(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        BookDto actual = bookService.save(requestDto);

        assertThat(actual).hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("isbn", requestDto.isbn())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("coverImage", requestDto.coverImage());
    }

    @Test
    @DisplayName("Receive a book by a valid id")
    public void getBookById_WithValidData_ShouldReturnBookDto() {
        Long id = 1L;

        Book book = new Book();
        book.setTitle("Turkey Trouble");
        book.setAuthor("Wendi Silvano");
        book.setIsbn("9780761455295");
        book.setPrice(BigDecimal.valueOf(19));
        book.setDescription("Awesome book");
        book.setCoverImage("image_1");
        book.setCategories(Set.of(new Category(id)));

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDto actual = bookService.findById(id);

        assertThat(actual).hasFieldOrPropertyWithValue("title", book.getTitle())
                .hasFieldOrPropertyWithValue("author", book.getAuthor())
                .hasFieldOrPropertyWithValue("isbn", book.getIsbn())
                .hasFieldOrPropertyWithValue("price", book.getPrice())
                .hasFieldOrPropertyWithValue("description", book.getDescription())
                .hasFieldOrPropertyWithValue("coverImage", book.getCoverImage());
    }

    @Test
    @DisplayName("Receive a book by non-valid id")
    public void getBookById_WithInvalidData_ShouldThrowException() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(id)
        );

        String expected = "can't find book with such id: " + id;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update a book by a valid id")
    public void update_WithValidData_ShouldReturnBookDto() {
        Long id = 1L;

        Book book = new Book();
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setIsbn("9780761455295");
        book.setPrice(BigDecimal.valueOf(29));
        book.setDescription("Test description");
        book.setCoverImage("Test image");
        book.setCategories(Set.of(new Category(id)));

        CreateBookRequestDto requestDto = new CreateBookRequestDto("Turkey Trouble",
                "Wendi Silvano",
                "9780761455296",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                List.of(id));

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        BookDto updated = bookService.updateById(id, requestDto);

        assertThat(updated).hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("isbn", requestDto.isbn())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("coverImage", requestDto.coverImage());
    }

    @Test
    @DisplayName("Receive a book by a non-valid id")
    public void update_WithInvalidData_ShouldThrowException() {
        Long id = 1L;

        CreateBookRequestDto requestDto = new CreateBookRequestDto("Turkey Trouble",
                "Wendi Silvano",
                "9780761455296",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                List.of(id));

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.updateById(id, requestDto)
        );

        String expected = "Can't find book with such id: " + id;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete a book by a valid id")
    public void deleteById_WithValidData_ShouldDoNothing() {
        Long id = 1L;

        bookService.deleteById(id);

        verify(bookRepository, times(1)).deleteById(id);
    }
}
