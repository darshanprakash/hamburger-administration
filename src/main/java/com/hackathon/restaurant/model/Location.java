package com.hackathon.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("location")
public class Location {

    @Id
    private String locationId;

    private String name;

    private String address;

    private String phone;

    private String latitude;

    private String longitude;

    @Field
    private boolean active = true;
}
