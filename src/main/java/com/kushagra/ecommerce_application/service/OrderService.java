package com.kushagra.ecommerce_application.service;

import com.kushagra.ecommerce_application.entity.Cart;
import com.kushagra.ecommerce_application.entity.Order;
import com.kushagra.ecommerce_application.entity.Product;
import com.kushagra.ecommerce_application.entity.User;
import com.kushagra.ecommerce_application.repository.CartRepository;
import com.kushagra.ecommerce_application.repository.OrderRepository;
import com.kushagra.ecommerce_application.repository.ProductRepository;
import com.kushagra.ecommerce_application.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> placeOrderByCart(Authentication auth){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart=user.getCart();
        if(cart==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Product> productList=user.getCart().getProductList();
        if(productList==null ||productList.isEmpty())return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Order order=new Order();
        order.setProductList(new ArrayList<>());
        order.setUser(user);
        int cost=0;
        for(Product product:productList){
            if(product!=null){
                order.getProductList().add(product);
                cost+=product.getPrice();
            }
        }
        order.setTotalCost(cost);
     orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> placeOrder(Authentication auth,ObjectId id){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        Product product=productRepository.findByid(id);
        if(user==null || product==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Order order=new Order();
        order.setUser(user);
        List<Product> productList=List.of(product);
        order.setProductList(productList);
        order.setTotalCost(product.getPrice());
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> getOrder(Authentication auth){
      User user=userRepository.findByusername(auth.getName()).orElse(null);
      if(user==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      Order order=orderRepository.findByUser_Id(user.getId());
      return new ResponseEntity<>(order,HttpStatus.OK);
    }
    public ResponseEntity<?> deleteOrder(Authentication auth){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        if(user==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Order order=orderRepository.findByUser_Id(user.getId());

            orderRepository.delete(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> addProduct(Authentication auth,ObjectId objectId){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        if(user==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Product product=productRepository.findByid(objectId);
        Order order=orderRepository.findByUser_Id(user.getId());
        order.getProductList().add(product);
        order.setTotalCost(order.getTotalCost()+product.getPrice());
           orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> removeProduct(Authentication auth,ObjectId objectId){
        User user=userRepository.findByusername(auth.getName()).orElse(null);
        if(user==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Product product=productRepository.findByid(objectId);
        Order order=orderRepository.findByUser_Id(user.getId());
        order.getProductList().remove(product);
        order.setTotalCost(order.getTotalCost()-product.getPrice());
        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
