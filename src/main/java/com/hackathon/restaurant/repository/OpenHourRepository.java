package com.hackathon.restaurant.repository;

import com.hackathon.restaurant.model.OpenHour;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpenHourRepository extends MongoRepository<OpenHour, String> {

    void deleteByDay(String day);
}
