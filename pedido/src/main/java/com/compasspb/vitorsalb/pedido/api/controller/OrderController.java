package com.compasspb.vitorsalb.pedido.api.controller;

import com.compasspb.vitorsalb.pedido.api.dto.OrderDto;
import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import com.compasspb.vitorsalb.pedido.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ms/v1/order")
public class OrderController {

    @Autowired
    private OrderService service;

//    @GetMapping
//    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, page = 0, sort = {"firstName"}) Pageable pageable) {
//        Page<ClientDto> products = service.findAll(pageable);
//        return ResponseEntity.ok(Mapper.pageableToDto(products, ClientDto.class));
//    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Order> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDto order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newOrder(order));
    }


}
