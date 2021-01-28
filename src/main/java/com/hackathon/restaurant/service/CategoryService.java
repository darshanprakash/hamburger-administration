package com.hackathon.restaurant.service;

import com.hackathon.restaurant.model.Category;
import com.hackathon.restaurant.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CategoryService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<List<Category>> getAllCategories(String name, int page, int limit) {
        try {
            Query query = new Query();
            if (name != null) {
                query.addCriteria(Criteria.where("name").is(name));
            }
            query.skip(page).limit(limit);
            List<Category> categories = mongoTemplate.find(query, Category.class);
            if (categories.isEmpty()) {
                log.info("No categories found");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("unable to fetch categories");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Category> getCategoryById(String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent())
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Category> createCategory(Category category) {
        try {
            Category _category = categoryRepository.save(category);
            return new ResponseEntity<>(_category, HttpStatus.OK);
        } catch (Exception e) {
            log.error("create category failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Category> updateCategory(String categoryId, Category category) {
        Optional<Category> categoryData = categoryRepository.findById(categoryId);
        try {
            if (categoryData.isPresent()) {
                Category _category = categoryData.get();
                _category.setName(category.getName());
                return new ResponseEntity<>(categoryRepository.save(_category), HttpStatus.OK);
            } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("update category failed, {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteCategory(String categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllCategories() {
        try {
            categoryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
