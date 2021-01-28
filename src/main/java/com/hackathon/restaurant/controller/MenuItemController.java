package com.hackathon.restaurant.controller;

import com.hackathon.restaurant.model.MenuItem;
import com.hackathon.restaurant.service.MenuItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Hamburger restaurant menu items API")
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;

    @GetMapping("/menu-item")
    public ResponseEntity<List<MenuItem>> getAllMenuItems(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String category,
                                                          @RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int limit,
                                                          @RequestParam(required = false, defaultValue = "true") boolean active) {
        return menuItemService.getAllMenuItems(name, category, page, limit, active);
    }

    @GetMapping("/menu-item/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable("id") String menuItemId) {
        return menuItemService.getMenuItemById(menuItemId);
    }

    @PostMapping("/menu-item")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.createMenuItem(menuItem);
    }

    @PutMapping("/menu-item/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable("id") String menuItemId, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(menuItemId, menuItem);
    }

    @DeleteMapping("/menu-item/{id}")
    public ResponseEntity<HttpStatus> deleteMenuItem(@PathVariable("id") String menuItemId) {
        return menuItemService.deleteMenuItem(menuItemId);
    }

    @DeleteMapping("/menu-item")
    public ResponseEntity<HttpStatus> deleteAllMenuItems() {
        return menuItemService.deleteAllMenuItems();
    }
}
