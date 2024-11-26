package com.compasspb.vitorsalb.estoque.api.controller;

import com.compasspb.vitorsalb.estoque.api.dto.PageableDto;
import com.compasspb.vitorsalb.estoque.api.dto.ProductDto;
import com.compasspb.vitorsalb.estoque.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.estoque.domain.entity.Product;
import com.compasspb.vitorsalb.estoque.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ms/v1/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, page = 0, sort = {"name"}) Pageable pageable) {
        Page<ProductDto> products = service.findAll(pageable);
        return ResponseEntity.ok(Mapper.pageableToDto(products, ProductDto.class));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PatchMapping("/add/{name}/{quantity}")
    public ResponseEntity<ProductDto> addQuantity(@PathVariable(value = "name") String name, @PathVariable(value = "quantity") Integer quantity) {
        return ResponseEntity.ok(service.addProduct(name, quantity));
    }

    @PatchMapping("/remove/{name}/{quantity}")
    public ResponseEntity<ProductDto> removeQuantity(@PathVariable(value = "name") String name, @PathVariable(value = "quantity") Integer quantity) {
        return ResponseEntity.ok(service.removeProduct(name, quantity));
    }
}
