package com.kushagra.ecommerce_application.repository;

import com.kushagra.ecommerce_application.entity.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    public Order findByUser_Id(ObjectId id);
}
