package com.kushagra.ecommerce_application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("Products")
public class Product {
    @Id
    private ObjectId id;
    private String name;
    private int price ;
    private int stock;
    private String type;
    private String description;


}
