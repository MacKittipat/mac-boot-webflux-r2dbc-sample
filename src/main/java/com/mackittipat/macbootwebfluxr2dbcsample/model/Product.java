package com.mackittipat.macbootwebfluxr2dbcsample.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("product")
@Data
public class Product {

    @Id
    private Long id;
    private UUID uuid = UUID.randomUUID();
    private String name;
    private Double price;
    private LocalDateTime createdTime = LocalDateTime.now();

}
