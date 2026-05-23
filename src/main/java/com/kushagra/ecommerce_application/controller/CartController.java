package com.kushagra.ecommerce_application.controller;

import com.kushagra.ecommerce_application.repository.CartRepository;
import com.kushagra.ecommerce_application.service.CartService;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add/id/{id}")
    public ResponseEntity<?> addProductToCart(Authentication auth,@PathVariable String id){
        ObjectId oid=new ObjectId(id);
        return cartService.addProductToCart(auth,oid);
    }
    @PostMapping("/remove/id/{id}")
    public ResponseEntity<?> removeProductToCart(Authentication auth, @PathVariable String id){
        ObjectId oid=new ObjectId(id);
        return cartService.removeProductToCart(auth,oid);
    }


}
