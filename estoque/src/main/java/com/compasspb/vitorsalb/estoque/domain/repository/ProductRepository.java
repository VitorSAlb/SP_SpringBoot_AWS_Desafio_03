package com.compasspb.vitorsalb.estoque.domain.repository;

import com.compasspb.vitorsalb.estoque.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Optional<Product> findByName(@Param("name") String name);

}
