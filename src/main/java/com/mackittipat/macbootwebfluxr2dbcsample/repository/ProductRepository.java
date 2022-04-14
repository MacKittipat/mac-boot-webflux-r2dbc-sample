package com.mackittipat.macbootwebfluxr2dbcsample.repository;

import com.mackittipat.macbootwebfluxr2dbcsample.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    @Query("SELECT * FROM product WHERE uuid=:uuid")
    public Mono<Product> findByUuid(UUID uuid);
}
