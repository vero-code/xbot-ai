package org.example.xbotai.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.xbotai.model.Category;
import org.example.xbotai.repository.CategoryRepository;
import org.example.xbotai.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
