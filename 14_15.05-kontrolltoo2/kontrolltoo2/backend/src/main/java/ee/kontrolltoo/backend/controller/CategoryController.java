package ee.kontrolltoo.backend.controller;

import ee.kontrolltoo.backend.entity.Category;
import ee.kontrolltoo.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("categories")
    public Category addCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            throw new RuntimeException("Cannot add category with id");
        }
        return categoryRepository.save(category);
    }

    @PutMapping("categories")
    public Category editCategory(@RequestBody Category category) {
        if (category.getId() == null) {
            throw new RuntimeException("Cannot add category without id");
        }
        return categoryRepository.save(category);
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }

    @GetMapping("categories/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }
}
