package com.kushagra.ecommerce_application.repository;

import com.kushagra.ecommerce_application.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    public Product findByid(ObjectId id);
    public Product findByname(String name);
}
