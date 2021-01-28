package com.hackathon.restaurant.controller;

import com.hackathon.restaurant.model.Location;
import com.hackathon.restaurant.service.LocationService;
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
@Api(value = "Hamburger restaurant location API")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/location")
    public ResponseEntity<List<Location>> getAllLocations(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int limit,
                                                          @RequestParam(required = false, defaultValue = "true") boolean active) {
        return locationService.getAllLocations(name, page, limit, active);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") String locationId) {
        return locationService.getLocationById(locationId);
    }

    @PostMapping("/location")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        return locationService.createLocation(location);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("id") String locationId, @RequestBody Location location) {
        return locationService.updateLocation(locationId, location);
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity<HttpStatus> deleteLocation(@PathVariable("id") String locationId) {
        return locationService.deleteLocation(locationId);
    }

    @DeleteMapping("/location")
    public ResponseEntity<HttpStatus> deleteAllLocations() {
        return locationService.deleteAllLocations();
    }
}
