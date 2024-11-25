package com.compasspb.vitorsalb.estoque.domain.service;

import com.compasspb.vitorsalb.estoque.domain.entity.Product;
import com.compasspb.vitorsalb.estoque.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product Not Founded: " + id); // change exception

        return optionalProduct.get();
    }

    public Product findByName(String name) {
        Optional<Product> optionalProduct = repository.findByName(name);
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product Not Founded: " + name); // change exception

        return optionalProduct.get();
    }

    public Product addProduct(String name, Integer quantity) {
        Product p = findByName(name);
        p.setQuantity(p.getQuantity() + quantity);
        return repository.save(p);
    }

    public Product removeProduct(String name, Integer quantity) {
        Product p = findByName(name);
        p.setQuantity(p.getQuantity() - quantity);
        return repository.save(p);
    }

    public Product updateProduct(Product product) {
        return null; // algum dia fazer esta funação kk
    }
}
