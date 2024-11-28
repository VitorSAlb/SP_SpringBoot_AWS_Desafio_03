package com.compasspb.vitorsalb.pedido.domain.service;


import com.compasspb.vitorsalb.pedido.api.controller.OrderController;
import com.compasspb.vitorsalb.pedido.api.dto.ClientDto;
import com.compasspb.vitorsalb.pedido.api.dto.OrderDto;
import com.compasspb.vitorsalb.pedido.api.dto.ProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.SimpleProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.ClientReturnDto;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.OrderReturnDto;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.ProductsReturnDto;
import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import com.compasspb.vitorsalb.pedido.domain.entity.ProductOrder;
import com.compasspb.vitorsalb.pedido.domain.repository.OrderRepository;
import com.compasspb.vitorsalb.pedido.infra.clients.ClientsResource;
import com.compasspb.vitorsalb.pedido.infra.clients.ProductResource;
import com.compasspb.vitorsalb.pedido.infra.config.ConfigProperties;
import com.compasspb.vitorsalb.pedido.infra.exceptions.BadRequestException;
import com.compasspb.vitorsalb.pedido.infra.exceptions.FeignException;
import com.compasspb.vitorsalb.pedido.infra.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
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

    @Autowired
    private ConfigProperties configProperties;


    @Transactional
    public OrderReturnDto newOrder(OrderDto order) {
        try {
            ClientDto clientDto = clientsResource.findByEmail(order.email()).getBody();
            if (clientDto == null) throw new NotFoundException("Client not found");

            ClientReturnDto clientReturnDto = new ClientReturnDto(clientDto.getEmail());

            List<SimpleProductDto> simpleProducts = order.products();
            if (simpleProducts == null || simpleProducts.isEmpty()) throw new BadRequestException("Product list is null or empty");

            List<ProductOrder> listProducts = simpleProducts.stream()
                    .map(p -> {
                        ProductDto dto = productResource.findByName(p.name()).getBody();
                        if (dto == null) throw new NotFoundException("Product not found: " + p.name());

                        log.info(dto.getId() + " -> name: " + dto.getName() + " quantity: " + p.quantity());
                        productResource.removeQuantity(dto.getName(), p.quantity());
                        return new ProductOrder(dto.getName(), p.quantity(), dto.getPrice());
                    }).toList();

            Order newOrder = new Order(clientDto.getEmail(), listProducts);
            Order entity = repository.save(newOrder);

            List<ProductsReturnDto> productsReturnDtos = listProducts.stream()
                    .map(product -> new ProductsReturnDto(product.getName(), product.getQuantity(), product.getPrice())).toList();

            Double total = listProducts.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();

            OrderReturnDto returnDto = new OrderReturnDto(newOrder.getId(), clientReturnDto, productsReturnDtos, total);
            returnDto.add(linkTo(methodOn(OrderController.class).findById(entity.getId())).withSelfRel());

            String clientUrl = configProperties.getClientUrl() + "/ms/v1/client/email/" + clientDto.getEmail();
            returnDto.add(Link.of(clientUrl).withRel("client"));

            listProducts.forEach(product -> {
                String productUrl = configProperties.getProductUrl() + "/ms/v1/product/name/" + product.getName();
                returnDto.add(Link.of(productUrl).withRel("product -> " + product.getName()));
            });

            return returnDto;
        } catch (RuntimeException e) {
            throw new FeignException("Error processing order: " + e.getMessage());
        }
    }

    public OrderReturnDto findById(Long id) {
        Order entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        List<ProductOrder> listProducts = entity.getProducts();
        List<ProductsReturnDto> productsReturnDtos = listProducts.stream()
                .map(product -> new ProductsReturnDto(product.getName(), product.getQuantity(), product.getPrice())).toList();

        Double total = listProducts.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
        OrderReturnDto returnDto = new OrderReturnDto(entity.getId(), new ClientReturnDto(entity.getEmail()), productsReturnDtos, total);

        returnDto.add(linkTo(methodOn(OrderController.class).findById(entity.getId())).withSelfRel());
        String clientUrl = configProperties.getClientUrl() + "/ms/v1/client/email/" + entity.getEmail();
        returnDto.add(Link.of(clientUrl).withRel("client"));

        listProducts.forEach(product -> {
            String productUrl = configProperties.getProductUrl() + "/ms/v1/product/name/" + product.getName();
            returnDto.add(Link.of(productUrl).withRel("product -> " + product.getName()));
        });
        return returnDto;
    }

    public Page<OrderReturnDto> findAll(Pageable pageable) {
        Page<Order> orders = repository.findAll(pageable);

        return orders.map(order -> {
            List<ProductOrder> listProducts = order.getProducts();
            List<ProductsReturnDto> productsReturnDtos = listProducts.stream()
                    .map(product -> new ProductsReturnDto(product.getName(), product.getQuantity(), product.getPrice())).toList();

            Double total = listProducts.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();

            OrderReturnDto returnDto = new OrderReturnDto(order.getId(), new ClientReturnDto(order.getEmail()), productsReturnDtos, total);
            returnDto.add(linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel());

            return returnDto;
        });
    }

    public Page<OrderReturnDto> findAllByEmail(Pageable pageable, String email) {
        Page<Order> orders = repository.findAllByEmail(pageable, email);

        if (orders.isEmpty()) throw new BadRequestException("This client not has orders");

        return orders.map(order -> {
            List<ProductOrder> listProducts = order.getProducts();
            List<ProductsReturnDto> productsReturnDtos = listProducts.stream()
                    .map(product -> new ProductsReturnDto(product.getName(), product.getQuantity(), product.getPrice())).toList();

            Double total = listProducts.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();

            OrderReturnDto returnDto = new OrderReturnDto(order.getId(), new ClientReturnDto(order.getEmail()), productsReturnDtos, total);
            returnDto.add(linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel());

            return returnDto;
        });
    }

}
