package com.kushagra.ecommerce_application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Document("cart_items")
public class Cart {
    @Id
    private ObjectId id;
    @DBRef
    private List<Product> productList=new ArrayList<>();

}
