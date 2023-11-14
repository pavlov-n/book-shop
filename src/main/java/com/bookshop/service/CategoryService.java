package com.bookshop.service;

import com.bookshop.dto.category.CategoryRequestDto;
import com.bookshop.dto.category.CategoryResponseDto;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto findById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryDto);

    CategoryResponseDto updateById(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);
}
