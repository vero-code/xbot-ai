package org.example.xbotai.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.xbotai.dto.CategorySelectionRequest;
import org.example.xbotai.model.Category;
import org.example.xbotai.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Get the user's selected category
     */
    @PostMapping("/select")
    public ResponseEntity<Category> selectCategory(@RequestBody CategorySelectionRequest request) {
        Category selectedCategory = categoryService.getCategoryById(request.categoryId());

        if (selectedCategory != null) {
            return ResponseEntity.ok(selectedCategory);
        } else {
            log.warn("CategoryController: Category with ID {} not found", request.categoryId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
