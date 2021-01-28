package com.hackathon.restaurant.service;

import com.hackathon.restaurant.model.MenuItem;
import com.hackathon.restaurant.repository.MenuItemRepository;
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
public class MenuItemService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    MenuItemRepository menuItemRepository;

    public MenuItemService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<List<MenuItem>> getAllMenuItems(String name, String category, int page, int limit, boolean active) {
        try {
            Query query = new Query();
            if (name != null) {
                query.addCriteria(Criteria.where("name").is(name));
            }
            if (category != null) {
                query.addCriteria(Criteria.where("category").is(category));
            }
            query.addCriteria(Criteria.where("active").is(active));
            query.skip(page).limit(limit);
            List<MenuItem> menuItems = mongoTemplate.find(query, MenuItem.class);
            if (menuItems.isEmpty()) {
                log.info("No menuItems found");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (Exception e) {
            log.error("unable to fetch menuItems");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MenuItem> getMenuItemById(String menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);
        if (menuItem.isPresent())
            return new ResponseEntity<>(menuItem.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<MenuItem> createMenuItem(MenuItem menuItem) {
        try {
            MenuItem _menuItem = menuItemRepository.save(menuItem);
            return new ResponseEntity<>(_menuItem, HttpStatus.OK);
        } catch (Exception e) {
            log.error("create menuItem failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MenuItem> updateMenuItem(String menuItemId, MenuItem menuItem) {
        Optional<MenuItem> menuItemData = menuItemRepository.findById(menuItemId);
        try {
            if (menuItemData.isPresent()) {
                MenuItem _menuItem = menuItemData.get();
                _menuItem.setName(menuItem.getName());
                _menuItem.setRegularPrice(menuItem.getRegularPrice());
                _menuItem.setComboPrice(menuItem.getComboPrice());
                _menuItem.setCategory(menuItem.getCategory());
                _menuItem.setActive(menuItem.isActive());
                return new ResponseEntity<>(menuItemRepository.save(_menuItem), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("update menuItem failed, {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteMenuItem(String menuItemId) {
        try {
            menuItemRepository.deleteById(menuItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllMenuItems() {
        try {
            menuItemRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
