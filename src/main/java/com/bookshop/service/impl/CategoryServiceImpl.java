package com.bookshop.service.impl;

import com.bookshop.dto.category.CategoryRequestDto;
import com.bookshop.dto.category.CategoryResponseDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.model.Category;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find category with such id: %d".formatted(id)));
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto updateById(Long id, CategoryRequestDto categoryDto) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find category with id: %d".formatted(id)));
        categoryMapper.updateCategory(categoryDto, categoryToUpdate);
        return categoryMapper.toDto(categoryRepository.save(categoryToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
