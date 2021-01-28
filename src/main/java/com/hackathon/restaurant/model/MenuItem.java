package com.hackathon.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("menuItem")
public class MenuItem {

    @Id
    private String itemId;

    private String name;

    private String regularPrice;

    private String comboPrice;

    private String category;

    @Field
    private boolean active = true;
}
