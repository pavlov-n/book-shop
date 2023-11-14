package com.bookshop.controller;

import com.bookshop.dto.book.BookDtoWithoutCategoryIds;
import com.bookshop.dto.category.CategoryRequestDto;
import com.bookshop.dto.category.CategoryResponseDto;
import com.bookshop.service.BookService;
import com.bookshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/categories")
@Tag(name = "Category management", description = "Endpoints for managing categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get all categories",
            description = "Receive a list of available categories")
    public List<CategoryResponseDto> getAll(@PageableDefault(size = 5) Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID",
            description = "Receive a category by ID")
    public CategoryResponseDto getCategoryById(@PathVariable @Positive Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/books")
    @Operation(summary = "Get book by category id",
            description = "Receive a list of books by category id")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable @Positive Long id,@PageableDefault(size = 5) Pageable pageable) {
        return bookService.findBooksByCategoryId(id, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create category",
            description = "Create a new category")
    public CategoryResponseDto createCategory(@RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update category",
            description = "Update a category by ID")
    public CategoryResponseDto updateCategory(
            @PathVariable @Positive Long id,@RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category",
            description = "Delete a category by ID")
    public void deleteCategory(@PathVariable @Positive Long id) {
        categoryService.deleteById(id);
    }
}
