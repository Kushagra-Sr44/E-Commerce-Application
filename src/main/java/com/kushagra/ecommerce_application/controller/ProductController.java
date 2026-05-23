package com.kushagra.ecommerce_application.controller;

import com.kushagra.ecommerce_application.entity.Product;
import com.kushagra.ecommerce_application.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("products/id/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id){
        ObjectId oid=new ObjectId(id);

        return productService.getProduct(oid);
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }
    @PutMapping("/product")
    public ResponseEntity<?> editProduct(@RequestBody Product product){
        return productService.editProduct(product);
    }
    @DeleteMapping("/product/id/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable  String id){
        ObjectId oid=new ObjectId(id);
        return productService.deleteProduct(oid);
    }
}
