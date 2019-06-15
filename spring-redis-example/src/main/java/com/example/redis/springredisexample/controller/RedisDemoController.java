package com.example.redis.springredisexample.controller;

import com.example.redis.springredisexample.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/rest"})
public class RedisDemoController {

    private static final String REDIS_INDEX_KEY = "PRODUCT";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping(value = {"products"})
    public String createProduct(@RequestBody Product product) {
        redisTemplate.opsForHash().put(REDIS_INDEX_KEY, product.getProductId(), product.toString());
        return "product is saved successfully";
    }

    @GetMapping(value = {"/products"})
    public ResponseEntity<Product> getProducts(@RequestBody Product product) {
        return new ResponseEntity(redisTemplate.opsForHash().entries(REDIS_INDEX_KEY), HttpStatus.OK);
    }

    @PutMapping(value = {"/products/{productId}"})
    public String updateProduct(@PathVariable("productId") String productId, @RequestBody Product product) {
        redisTemplate.opsForHash().put(REDIS_INDEX_KEY, productId, product.toString());
        return "Product is updated successfully";
    }

    @DeleteMapping(value = {"/products/{productId}"})
    public String deleteProduct(@PathVariable("productId") String productId) {
        redisTemplate.opsForHash().delete(REDIS_INDEX_KEY, productId);
        return "Product is deleted successfully";
    }

    @GetMapping(value = {"/products/{productId}"})
    public String getProdcutById(@PathVariable("productId") String productId) {
        return (String) redisTemplate.opsForHash().get(REDIS_INDEX_KEY, productId);
    }
}
