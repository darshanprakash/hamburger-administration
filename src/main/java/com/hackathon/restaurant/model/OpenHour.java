package com.hackathon.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("openHour")
public class OpenHour {

    @Id
    private String id;

    private String day;

    private String openTime;

    private String closeTime;
}
