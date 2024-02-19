package com.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookshop.dto.category.CategoryRequestDto;
import com.bookshop.dto.category.CategoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/add-three-default-categories.sql")
@Sql(scripts = "classpath:database/delete-all-from-categories.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTests {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a category with valid data")
    void createCategory_ValidRequestDto_ShouldReturnCategoryDto() throws Exception {
        Long id = 4L;

        CategoryRequestDto requestDto = new CategoryRequestDto("Fiction",
                "Awesome category");

        CategoryResponseDto expected = new CategoryResponseDto(id, "Fiction", "Awesome category");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);
        assertEquals(expected, actual);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Receive all available categories")
    void getAll_WithCategoriesInDb_ShouldReturnAllCategories() throws Exception {
        List<CategoryResponseDto> expected = List.of(new CategoryResponseDto(1L,
                        "First category",
                        "First description"),
                new CategoryResponseDto(2L,
                        "Second category",
                        "Second description"),
                new CategoryResponseDto(3L,
                        "Third category",
                        "Third description"));

        MvcResult result = mockMvc.perform(
                        get("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                CategoryResponseDto[].class);
        assertEquals(3, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Receive a category by a valid id")
    void getCategoryById_WithCategoriesInDb_ShouldReturnCategoryDto() throws Exception {
        Long id = 1L;

        CategoryResponseDto expected = new CategoryResponseDto(
                id, "First category", "First description");

        MvcResult result = mockMvc.perform(
                        get("/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);
        assertEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete a category with valid id")
    void deleteCategory_WithValidData_ShouldDoNothing() throws Exception {
        MvcResult result = mockMvc.perform(
                        delete("/categories/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update a category with valid id")
    void updateCategory_withValidData_ShouldReturnCategoryDto() throws Exception {
        Long id = 1L;

        CategoryRequestDto requestDto = new CategoryRequestDto("Fiction",
                "Awesome category");

        CategoryResponseDto expected = new CategoryResponseDto(id, "Fiction", "Awesome category");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/categories/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertEquals(expected, actual);
    }
}
