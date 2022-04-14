package com.mackittipat.macbootwebfluxr2dbcsample.controller;

import com.mackittipat.macbootwebfluxr2dbcsample.model.Product;
import com.mackittipat.macbootwebfluxr2dbcsample.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequestMapping("products")
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Flux<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @GetMapping("{uuid}")
    public Mono<ResponseEntity<Product>> getProductByUuid(@PathVariable UUID uuid) {
        return productRepository.findByUuid(uuid).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productRepository.save(product).map(ResponseEntity::ok);
    }

}