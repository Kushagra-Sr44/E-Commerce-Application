package com.kushagra.ecommerce_application.repository;

import com.kushagra.ecommerce_application.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public Optional<User> findByusername(String name);
}
