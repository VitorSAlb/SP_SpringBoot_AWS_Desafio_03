package com.compasspb.vitorsalb.client.domain.service;

import com.compasspb.vitorsalb.client.api.controller.ClientController;
import com.compasspb.vitorsalb.client.api.dto.ClientDto;
import com.compasspb.vitorsalb.client.api.dto.PageableDto;
import com.compasspb.vitorsalb.client.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.client.domain.entity.Client;
import com.compasspb.vitorsalb.client.domain.repository.ClientRepository;
import com.compasspb.vitorsalb.client.infra.clients.OrderResource;
import com.compasspb.vitorsalb.client.infra.exceptions.DuplicateException;
import com.compasspb.vitorsalb.client.infra.exceptions.FeignException;
import com.compasspb.vitorsalb.client.infra.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientRepository.class);

    @Autowired
    private ClientRepository repository;

    @Autowired
    private OrderResource orderResource;

    @Transactional
    public ClientDto create(ClientDto clientDto) {

        if (repository.existsByEmail(clientDto.getEmail())) throw new DuplicateException("Email already exists");

        log.info("Creating one client!");

        Client entity = Mapper.toEntity(clientDto, Client.class);
        ClientDto dto = Mapper.toDto(repository.save(entity), ClientDto.class);
        dto.add(linkTo(methodOn(ClientController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public Page<ClientDto> findAll(Pageable pageable) {
        log.info("Finding all client!");

        Page<Client> products = repository.findAllp(pageable);
        Page<ClientDto> dtos = Mapper.mapEntityPageToDtoPage(products, ClientDto.class);
        dtos.forEach(d -> d.add(linkTo(methodOn(ClientController.class).findById(d.getId())).withSelfRel()));
        return dtos;
    }

    public ClientDto findById(Long id) {
        log.info("Finding one client with ID!");

        Client entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No records found for this ID!"));
        ClientDto dto = Mapper.toDto(entity, ClientDto.class);
        dto.add(linkTo(methodOn(ClientController.class).findById(id)).withSelfRel());

        int orders = 0;
        try {
            orders = Objects.requireNonNull(orderResource.findAllByEmail(null, entity.getEmail()).getBody()).getTotalElements();
        } catch (FeignException e) {
            throw new FeignException("Error to catch orders of this Client");
        }
        dto.setTotalOrders(orders);
        return dto;
    }

    public ClientDto findByEmail(String email) {
        log.info("Finding one client with email!");

        Client entity = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No records found for this email!"));
        ClientDto dto = Mapper.toDto(entity, ClientDto.class);
        dto.add(linkTo(methodOn(ClientController.class).findById(dto.getId())).withSelfRel());
        int orders = 0;
        try {
            orders = Objects.requireNonNull(orderResource.findAllByEmail(null, entity.getEmail()).getBody()).getTotalElements();
        } catch (RuntimeException e) {
            throw new FeignException("Error to catch orders of this Client");
        }
        dto.setTotalOrders(orders);
        return dto;
    }

    public PageableDto notExistsByEmail(Pageable peageble, String email) {
        if (!repository.existsByEmail(email)) throw new DuplicateException("Email don't exists");
        return orderResource.findAllByEmail(peageble, email).getBody();
    }

    @Transactional
    public ClientDto update(ClientDto clientDto, String email) {

        Client client = Mapper.toEntity(findByEmail(email), Client.class);

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setBirthday(clientDto.getBirthday());

        Client entity = repository.save(client);
        ClientDto dto = Mapper.toDto(repository.save(entity), ClientDto.class);
        dto.add(linkTo(methodOn(ClientController.class).findById(dto.getId())).withSelfRel());

        return dto;
    }

}
