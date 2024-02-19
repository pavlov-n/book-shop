package com.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookshop.dto.category.CategoryRequestDto;
import com.bookshop.dto.category.CategoryResponseDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.mapper.impl.CategoryMapperImpl;
import com.bookshop.model.Category;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.impl.CategoryServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    @DisplayName("Receive a category by a valid id")
    public void getCategoryById_WithValidData_ShouldReturnCategoryDto() {
        Long id = 1L;

        Category category = new Category();
        category.setId(id);
        category.setName("Fiction");
        category.setDescription("Awesome category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryResponseDto actual = categoryService.findById(id);

        assertThat(actual).hasFieldOrPropertyWithValue("name", category.getName())
                .hasFieldOrPropertyWithValue("description", category.getDescription());
    }

    @Test
    @DisplayName("Receive a category by a non-valid id")
    public void getCategoryById_WithInvalidData_ShouldThrowException() {
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.findById(id)
        );

        String expected = "Can't find category with such id: " + id;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create category with valid data")
    public void save_WithValidData_ShouldReturnCategoryDto() {
        Long id = 1L;

        CategoryRequestDto requestDto = new CategoryRequestDto("Fiction",
                "Awesome category");

        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());

        when(categoryMapper.toCategory(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponseDto actual = categoryService.save(requestDto);

        assertThat(actual).hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @DisplayName("Update category by a valid id")
    public void update_WithValidData_ShouldReturnCategoryDto() {
        Long id = 1L;

        Category category = new Category();
        category.setName("Test name");
        category.setDescription("Test description");

        CategoryRequestDto requestDto = new CategoryRequestDto("Fiction",
                "Awesome category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponseDto updated = categoryService.updateById(id, requestDto);

        assertThat(updated).hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @DisplayName("Update category by a non-valid id")
    public void update_WithInvalidData_ShouldThrowException() {
        Long id = 1L;

        CategoryRequestDto requestDto = new CategoryRequestDto("Fiction",
                "Awesome category");

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.updateById(id, requestDto)
        );

        String expected = "Can't find category with id: " + id;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete category by a valid id")
    public void deleteById_WithValidData_ShouldDoNothing() {
        Long id = 1L;

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }
}
