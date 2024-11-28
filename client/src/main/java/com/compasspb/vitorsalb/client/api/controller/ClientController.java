package com.compasspb.vitorsalb.client.api.controller;

import com.compasspb.vitorsalb.client.api.dto.ClientDto;
import com.compasspb.vitorsalb.client.api.dto.PageableDto;
import com.compasspb.vitorsalb.client.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.client.domain.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ms/v1/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, page = 0, sort = {"firstName"}) Pageable pageable) {
        Page<ClientDto> products = service.findAll(pageable);
        return ResponseEntity.ok(Mapper.pageableToDto(products, ClientDto.class));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDto> findByEmail(@PathVariable(value = "email") String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<ClientDto> update(@PathVariable(value = "email") String email, @RequestBody @Valid ClientDto dto) {
        return ResponseEntity.ok(service.update(dto, email));
    }

}
