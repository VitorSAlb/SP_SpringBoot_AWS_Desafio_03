package com.compasspb.vitorsalb.estoque.domain.service;

import com.compasspb.vitorsalb.estoque.api.controller.ProductController;
import com.compasspb.vitorsalb.estoque.api.dto.ProductDto;
import com.compasspb.vitorsalb.estoque.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.estoque.domain.entity.Product;
import com.compasspb.vitorsalb.estoque.domain.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    public ProductDto save(ProductDto product) {

        if (product == null) throw new RuntimeException("Required Objects Is Null"); // change exception

        log.info("Creating one product!");

        Product entity = Mapper.toEntity(product, Product.class);
        ProductDto dto = Mapper.toDto(repository.save(entity), ProductDto.class);
        dto.add(linkTo(methodOn(ProductController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public Page<ProductDto> findAll(Pageable pageable) {
        log.info("Finding all products!");

        Page<Product> products = repository.findAllp(pageable);
        Page<ProductDto> dtos = Mapper.mapEntityPageToDtoPage(products, ProductDto.class);
        dtos.forEach(d -> d.add(linkTo(methodOn(ProductController.class).findById(d.getId())).withSelfRel()));
        return dtos;
    }

    public ProductDto findById(Long id) {
        log.info("Finding one product with ID!");

        Product entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found for this ID!"));
        ProductDto dto = Mapper.toDto(entity, ProductDto.class);
        dto.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return dto;
    }

    public ProductDto findByName(String name) {
        log.info("Finding one product with name!");

        Product entity = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("No records found for this Name!"));
        ProductDto dto = Mapper.toDto(entity, ProductDto.class);
        dto.add(linkTo(methodOn(ProductController.class).findById(entity.getId())).withSelfRel());
        return dto;
    }

    public ProductDto addProduct(String name, Integer quantity) {
        log.info("Adding products!");

        ProductDto p = findByName(name);

        Product entity = Mapper.toEntity(p, Product.class);
        entity.setQuantity(entity.getQuantity() + quantity);
        ProductDto dto = Mapper.toDto(repository.save(entity), ProductDto.class);
        dto.add(linkTo(methodOn(ProductController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public ProductDto removeProduct(String name, Integer quantity) {
        log.info("Removing products!");

        ProductDto p = findByName(name);
        Product entity = Mapper.toEntity(p, Product.class);

        if (quantity > entity.getQuantity()) throw new RuntimeException("The value inserted is more than of quantity in stock");

        entity.setQuantity(entity.getQuantity() - quantity);
        ProductDto dto = Mapper.toDto(repository.save(entity), ProductDto.class);
        dto.add(linkTo(methodOn(ProductController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }
}
