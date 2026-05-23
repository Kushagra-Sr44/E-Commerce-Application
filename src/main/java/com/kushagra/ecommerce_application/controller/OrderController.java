package com.kushagra.ecommerce_application.controller;

import com.kushagra.ecommerce_application.service.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public ResponseEntity<?> getOrder(Authentication auth){
        return orderService.getOrder(auth);
    }
    @PostMapping("/id/{id}")
    public ResponseEntity<?> placeOrder(Authentication auth,@PathVariable  String id){
        ObjectId oid=new ObjectId(id);
        return orderService.placeOrder(auth,oid);
    }
    @PostMapping
    public ResponseEntity<?> placeOrderByCart(Authentication auth){
        return orderService.placeOrderByCart(auth);
    }
    @PutMapping("/add/id/{id}")
    public ResponseEntity<?> addProduct(Authentication auth,@PathVariable String id){
        ObjectId oid=new ObjectId(id);
        return orderService.addProduct(auth,oid);
    }
    @PutMapping("/remove/id/{id}")
    public ResponseEntity<?> removeProduct(Authentication auth,@PathVariable String id){
        ObjectId oid=new ObjectId(id);
        return orderService.removeProduct(auth,oid);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteOrder(Authentication auth){
        return orderService.deleteOrder(auth);
    }

}
