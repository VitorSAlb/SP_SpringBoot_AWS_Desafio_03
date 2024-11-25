package com.compasspb.vitorsalb.estoque.api.controller;

import com.compasspb.vitorsalb.estoque.api.dto.ProductDto;
import com.compasspb.vitorsalb.estoque.domain.entity.Product;
import com.compasspb.vitorsalb.estoque.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ms/v1/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/id/{id}")
    public ProductDto findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }
}
