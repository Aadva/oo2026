package ee.mirko.veebipood.controller;

import ee.mirko.veebipood.entity.Category;
import ee.mirko.veebipood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("categories")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id); // kustutan
        return categoryRepository.findAll(); // uuenenud seis
    }

    @PostMapping("categories")
    public List<Category> addCategory(@RequestBody Category category){
        categoryRepository.save(category); // siin salvestab
        return categoryRepository.findAll(); // siin on uuenenud seis
    }

}