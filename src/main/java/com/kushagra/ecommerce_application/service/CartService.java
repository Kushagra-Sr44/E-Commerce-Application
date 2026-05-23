package com.kushagra.ecommerce_application.service;

import com.kushagra.ecommerce_application.entity.Cart;
import com.kushagra.ecommerce_application.entity.Product;
import com.kushagra.ecommerce_application.entity.User;
import com.kushagra.ecommerce_application.repository.CartRepository;
import com.kushagra.ecommerce_application.repository.ProductRepository;
import com.kushagra.ecommerce_application.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    public ResponseEntity<?> addProductToCart(Authentication auth, ObjectId id){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        Cart cart=user.getCart();
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(cart==null) cart=new Cart();
        Product product =productRepository.findByid(id);
        cart.getProductList().add(product);
        user.setCart(cart);
        cartRepository.save(cart);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> removeProductToCart(Authentication auth, ObjectId id){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        Cart cart=user.getCart();
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(cart==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Product product =productRepository.findByid(id);
        cart.getProductList().remove(product);
        user.setCart(cart);
        cartRepository.save(cart);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
