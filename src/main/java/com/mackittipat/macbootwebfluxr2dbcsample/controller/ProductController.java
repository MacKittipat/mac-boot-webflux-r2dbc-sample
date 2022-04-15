package com.mackittipat.macbootwebfluxr2dbcsample.controller;

import com.mackittipat.macbootwebfluxr2dbcsample.model.Product;
import com.mackittipat.macbootwebfluxr2dbcsample.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
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

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getProductStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(sequence -> "Product - " + LocalTime.now().toString());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<String> upload(@RequestPart("files") Flux<FilePart> filePartFlux) {
        return filePartFlux.flatMap(filePart ->
                filePart.content().map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return new String(bytes, StandardCharsets.UTF_8);
                }));
    }
}
