package com.kushagra.ecommerce_application.service;

import com.kushagra.ecommerce_application.entity.Product;
import com.kushagra.ecommerce_application.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public ResponseEntity<?>getProduct( ObjectId id) {
        Product product=productRepository.findByid(id);
        if (product==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    public ResponseEntity<?>addProduct(Product product) {
        String name=product.getName();
        Product oldProduct=productRepository.findByname(name);
        if (oldProduct!=null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productRepository.save(product);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    public ResponseEntity<?>editProduct( Product newProduct) {
        Product  oldproduct=productRepository.findByid(newProduct.getId());
        if (oldproduct==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productRepository.delete(oldproduct);
        productRepository.save(newProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?>deleteProduct(ObjectId id) {
        Product  product=productRepository.findByid(id);
        if (product==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productRepository.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
