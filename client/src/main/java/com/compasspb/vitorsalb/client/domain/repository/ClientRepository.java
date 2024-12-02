package com.compasspb.vitorsalb.client.domain.repository;

import com.compasspb.vitorsalb.client.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    @Query("SELECT c FROM Client c")
    Page<Client> findAllp(Pageable pageable);

    boolean existsByEmail(String email);
}
