package com.hackathon.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-failed")
public class AuthenticationController {

    @GetMapping
    public ResponseEntity<Object> authFailed() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
