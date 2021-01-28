package com.hackathon.restaurant.service;

import com.hackathon.restaurant.model.OpenHour;
import com.hackathon.restaurant.repository.OpenHourRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class OpenHourService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    OpenHourRepository openHourRepository;

    public OpenHourService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<List<OpenHour>> getAllOpenHours() {
        try {
            List<OpenHour> openHourList = mongoTemplate.findAll(OpenHour.class);
            if (openHourList.isEmpty()) {
                log.info("No open hours found");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(openHourList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("unable to fetch open hours");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<OpenHour> getOpenHourByDay(String day) {
        Query query = new Query();
        query.addCriteria(Criteria.where("day").is(day));
        OpenHour openHour = mongoTemplate.findOne(query, OpenHour.class);
        if (openHour == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(openHour, HttpStatus.OK);
    }

    public ResponseEntity<List<OpenHour>> createAllOpenHours(OpenHour openHour) {
        List<String> days = Arrays.asList("mon", "tue", "wed", "thu", "fri", "sat", "sun");
        try {
            for (String day : days) {
                OpenHour _openHour = new OpenHour();
                _openHour.setDay(day);
                _openHour.setOpenTime(openHour.getOpenTime());
                _openHour.setCloseTime(openHour.getCloseTime());
                openHourRepository.save(_openHour);
            }
            return new ResponseEntity<>(openHourRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("create all open hours failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<OpenHour> createAllOpenHours(String day, OpenHour openHour) {
        try {
            OpenHour _openHour = new OpenHour();
            _openHour.setDay(day);
            _openHour.setOpenTime(openHour.getOpenTime());
            _openHour.setCloseTime(openHour.getCloseTime());
            return new ResponseEntity<>(openHourRepository.save(_openHour), HttpStatus.OK);
        } catch (Exception e) {
            log.error("create open hour failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<OpenHour> updateOpenHourByDay(String day, OpenHour openHour) {
        Query query = new Query();
        query.addCriteria(Criteria.where("day").is(day));
        OpenHour openHourData = mongoTemplate.findOne(query, OpenHour.class);
        try {
            if (openHourData != null) {
                openHourData.setOpenTime(openHour.getOpenTime());
                openHourData.setCloseTime(openHour.getCloseTime());
                return new ResponseEntity<>(openHourRepository.save(openHourData), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Update open hour by day failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<OpenHour>> updateAllOpenHours(OpenHour openHour) {
        List<String> days = Arrays.asList("mon", "tue", "wed", "thu", "fri", "sat", "sun");
        try {
            for (String day : days) {
                Query query = new Query();
                query.addCriteria(Criteria.where("day").is(day));
                OpenHour openHourData = mongoTemplate.findOne(query, OpenHour.class);
                if (openHourData != null) {
                    openHourData.setOpenTime(openHour.getOpenTime());
                    openHourData.setCloseTime(openHour.getCloseTime());
                } else {
                    openHourData = new OpenHour();
                    openHourData.setDay(day);
                    openHourData.setOpenTime(openHour.getOpenTime());
                    openHourData.setCloseTime(openHour.getCloseTime());
                }
                openHourRepository.save(openHourData);
            }
            return new ResponseEntity<>(openHourRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Update open hour by day failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteOpenHourByDay(String day) {
        try {
            openHourRepository.deleteByDay(day);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllOpenHours() {
        try {
            openHourRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
