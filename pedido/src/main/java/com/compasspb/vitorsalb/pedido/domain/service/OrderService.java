package com.compasspb.vitorsalb.pedido.domain.service;


import com.compasspb.vitorsalb.pedido.api.dto.ClientDto;
import com.compasspb.vitorsalb.pedido.api.dto.OrderDto;
import com.compasspb.vitorsalb.pedido.api.dto.ProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import com.compasspb.vitorsalb.pedido.domain.entity.ProductOrder;
import com.compasspb.vitorsalb.pedido.domain.repository.OrderRepository;
import com.compasspb.vitorsalb.pedido.infra.clients.ClientsResource;
import com.compasspb.vitorsalb.pedido.infra.clients.ProductResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ClientsResource clientsResource;

    @Autowired
    private ProductResource productResource;


    @Transactional
    public Order newOrder(OrderDto order) {

        try {
            ClientDto clientDto = clientsResource.findByEmail(order.email()).getBody();

            if (clientDto == null) throw new RuntimeException("Client not founded");

            List<ProductOrder> listProducts = order.simpleProductDtoList().stream().map(p -> {
                ProductDto dto = productResource.findById(p.name()).getBody();

                if (dto == null) throw new RuntimeException("Product not founded");

                productResource.removeQuantity(p.name(), p.quantity());
                return new ProductOrder(dto.getName(), p.quantity(), dto.getPrice());
            }).toList();

            Order newOrder = new Order(clientDto.getEmail(), listProducts);
            return repository.save(newOrder);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error");
        }
    }

    public Order findById(Long id) {
        return Mapper.toEntity(repository.findById(id), Order.class);
    }

}
