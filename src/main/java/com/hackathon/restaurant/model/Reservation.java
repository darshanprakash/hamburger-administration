package com.hackathon.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("reservation")
public class Reservation {

    @Id
    private String reservationId;

    private String name;

    private String size;

    private String phone;

    private String startDateAndTime;

    private String endDateAndTime;
}
