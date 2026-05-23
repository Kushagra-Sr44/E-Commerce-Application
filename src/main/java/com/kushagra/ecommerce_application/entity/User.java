package com.kushagra.ecommerce_application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document("users")
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private List<String> roles;
   @DBRef
    private Cart cart;
}
