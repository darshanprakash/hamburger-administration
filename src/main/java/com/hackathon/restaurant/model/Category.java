package com.hackathon.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("category")
public class Category {

    @Id
    private String categoryId;

    private String name;
}
