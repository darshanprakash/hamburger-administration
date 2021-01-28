package com.hackathon.restaurant.controller;

import com.hackathon.restaurant.model.OpenHour;
import com.hackathon.restaurant.service.OpenHourService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Hamburger restaurant open hours API")
public class OpenHourController {

    @Autowired
    OpenHourService openHourService;

    @GetMapping("/open-hour")
    public ResponseEntity<List<OpenHour>> getAllOpenHours() {
        return openHourService.getAllOpenHours();
    }

    @GetMapping("/open-hour/{day}")
    public ResponseEntity<OpenHour> getOpenHourByDay(@PathVariable("day") String day) {
        return openHourService.getOpenHourByDay(day);
    }

    @PostMapping("/open-hour")
    public ResponseEntity<List<OpenHour>> createAllOpenHours(@RequestBody OpenHour openHour) {
        return openHourService.createAllOpenHours(openHour);
    }

    @PostMapping("/open-hour/{day}")
    public ResponseEntity<OpenHour> createAllOpenHours(@PathVariable("day") String day, @RequestBody OpenHour openHour) {
        return openHourService.createAllOpenHours(day, openHour);
    }

    @PutMapping("/open-hour/{day}")
    public ResponseEntity<OpenHour> updateOpenHourByDay(@PathVariable("day") String day, @RequestBody OpenHour openHour) {
        return openHourService.updateOpenHourByDay(day, openHour);
    }

    @PutMapping("/open-hour")
    public ResponseEntity<List<OpenHour>> updateAllOpenHours(@RequestBody OpenHour openHour) {
        return openHourService.updateAllOpenHours(openHour);
    }

    @DeleteMapping("/open-hour/{day}")
    public ResponseEntity<HttpStatus> deleteOpenHourByDay(@PathVariable("day") String day) {
        return openHourService.deleteOpenHourByDay(day);
    }

    @DeleteMapping("/open-hour")
    public ResponseEntity<HttpStatus> deleteAllOpenHours() {
        return openHourService.deleteAllOpenHours();
    }
}
