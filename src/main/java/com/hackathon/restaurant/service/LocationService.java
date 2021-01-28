package com.hackathon.restaurant.service;

import com.hackathon.restaurant.model.Location;
import com.hackathon.restaurant.repository.LocationRepository;
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
public class LocationService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    LocationRepository locationRepository;

    public LocationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<List<Location>> getAllLocations(String name, int page, int limit, boolean active) {
        try {
            Query query = new Query();
            if (name != null) {
                query.addCriteria(Criteria.where("name").is(name));
            }
            query.addCriteria(Criteria.where("active").is(active));
            query.skip(page).limit(limit);
            List<Location> locations = mongoTemplate.find(query, Location.class);
            if (locations.isEmpty()) {
                log.info("No locations found");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("unable to fetch locations");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Location> getLocationById(String locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isPresent())
            return new ResponseEntity<>(location.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Location> createLocation(Location location) {
        try {
            Location _location = locationRepository.save(location);
            return new ResponseEntity<>(_location, HttpStatus.OK);
        } catch (Exception e) {
            log.error("create location failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Location> updateLocation(String locationId, Location location) {
        Optional<Location> locationData = locationRepository.findById(locationId);
        try {
            if (locationData.isPresent()) {
                Location _location = locationData.get();
                _location.setName(location.getName());
                _location.setAddress(location.getAddress());
                _location.setPhone(location.getPhone());
                _location.setLatitude(location.getLatitude());
                _location.setLongitude(location.getLongitude());
                _location.setActive(location.isActive());
                return new ResponseEntity<>(locationRepository.save(_location), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("update location failed, {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteLocation(String locationId) {
        try {
            locationRepository.deleteById(locationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllLocations() {
        try {
            locationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}