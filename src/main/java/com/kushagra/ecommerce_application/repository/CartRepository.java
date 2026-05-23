package com.kushagra.ecommerce_application.repository;

import com.kushagra.ecommerce_application.entity.Cart;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, ObjectId> {
}
