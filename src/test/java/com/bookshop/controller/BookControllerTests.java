package com.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookshop.dto.book.BookDto;
import com.bookshop.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/add-three-default-books.sql")
@Sql(scripts = {"classpath:database/delete-all-from-books-and-categories.sql",
        "classpath:database/delete-all-from-categories.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create book with valid data")
    void createBook_ValidRequestDto_ShouldReturnBookDto() throws Exception {
        Long id = 4L;

        CreateBookRequestDto requestDto = new CreateBookRequestDto("Turkey Trouble",
                "Wendi Silvano",
                "9780761455296",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                List.of(1L));

        BookDto expected = new BookDto(id,
                requestDto.title(),
                requestDto.author(),
                requestDto.isbn(),
                requestDto.price(),
                requestDto.description(),
                requestDto.coverImage(),
                requestDto.categoriesIds());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Receive all books from catalogue")
    void getAll_WithBooksInCatalog_ShouldReturnAllBooks() throws Exception {
        BookDto[] expected = {new BookDto(1L,
                "First book",
                "First author",
                "9780761455295",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                Collections.emptyList()),
                new BookDto(2L,
                        "Second book",
                        "Second author",
                        "9680761455295",
                        BigDecimal.valueOf(19),
                        "Awesome book",
                        "image_2",
                        Collections.emptyList()),
                new BookDto(3L,
                        "Third book",
                        "Third author",
                        "9580761455295",
                        BigDecimal.valueOf(19),
                        "Awesome book",
                        "image_3",
                        Collections.emptyList())};

        MvcResult result = mockMvc.perform(
                        get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                BookDto[].class);
        assertEquals(3, actual.length);
        assertArrayEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Receive a book by a valid id")
    void getBookById_WithBooksInCatalog_ShouldReturnBookDto() throws Exception {
        BookDto expected = new BookDto(1L,
                "First book",
                "First author",
                "9780761455295",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                Collections.emptyList());

        MvcResult result = mockMvc.perform(
                        get("/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete a book by a valid id")
    void delete_WithValidData_ShouldDoNothing() throws Exception {
        ResultActions result = mockMvc.perform(
                        delete("/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update a book by a valid id")
    void update_withValidData_ShouldReturnBookDto() throws Exception {
        Long id = 1L;

        CreateBookRequestDto requestDto = new CreateBookRequestDto("Turkey Trouble",
                "Wendi Silvano",
                "9780761455296",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                List.of(id));

        BookDto expected = new BookDto(1L,
                "Turkey Trouble",
                "Wendi Silvano",
                "9780761455296",
                BigDecimal.valueOf(19),
                "Awesome book",
                "image_1",
                List.of(id));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/books/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertEquals(expected, actual);
    }
}
