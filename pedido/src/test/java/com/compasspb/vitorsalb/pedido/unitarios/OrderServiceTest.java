package com.compasspb.vitorsalb.pedido.unitarios;

import com.compasspb.vitorsalb.pedido.api.dto.ClientDto;
import com.compasspb.vitorsalb.pedido.api.dto.OrderDto;
import com.compasspb.vitorsalb.pedido.api.dto.ProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.SimpleProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.OrderReturnDto;
import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import com.compasspb.vitorsalb.pedido.domain.entity.ProductOrder;
import com.compasspb.vitorsalb.pedido.domain.repository.OrderRepository;
import com.compasspb.vitorsalb.pedido.domain.service.OrderService;
import com.compasspb.vitorsalb.pedido.infra.clients.ClientsResource;
import com.compasspb.vitorsalb.pedido.infra.clients.ProductResource;
import com.compasspb.vitorsalb.pedido.infra.config.ConfigProperties;
import com.compasspb.vitorsalb.pedido.infra.exceptions.BadRequestException;
import com.compasspb.vitorsalb.pedido.infra.exceptions.FeignException;
import com.compasspb.vitorsalb.pedido.infra.exceptions.NotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientsResource clientsResource;

    @Mock
    private ProductResource productResource;

    @Mock
    private ConfigProperties configProperties;

    @InjectMocks
    private OrderService orderService;

    @Test
    void newOrder_ShouldCreateOrder_WhenValidInput() {
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("test@example.com");
        when(clientsResource.findByEmail("test@example.com")).thenReturn(ResponseEntity.ok(clientDto));
        when(configProperties.getClientUrl()).thenReturn("www.localhost:testC");
        when(configProperties.getProductUrl()).thenReturn("www.localhost:testP");

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Product A");
        productDto.setPrice(100.0);
        productDto.setQuantity(10);
        when(productResource.findByName("Product A")).thenReturn(ResponseEntity.ok(productDto));

        Order mockOrder = new Order("test@example.com", List.of(new ProductOrder("Product A", 2, 100.0)));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDto orderDto = new OrderDto("test@example.com", List.of(new SimpleProductDto("Product A", 2)));

        OrderReturnDto result = orderService.newOrder(orderDto);

        String clientLink = result.getLinks("client").toString().substring(2, 57);

        assertNotNull(result);
        assertEquals("test@example.com", result.getClient().getEmail());
        assertEquals(200.0, result.getTotal());
        assertEquals("www.localhost:testC/ms/v1/client/email/test@example.com", clientLink);
        verify(productResource).removeQuantity("Product A", 2);
        verify(productResource).findByName("Product A");
        verify(productResource).removeQuantity("Product A", 2);
    }

    @Test
    void newOrder_ShouldThrowException_WhenClientNotFound() {
        when(clientsResource.findByEmail("invalid@example.com")).thenReturn(ResponseEntity.of(Optional.empty()));

        OrderDto orderDto = new OrderDto("invalid@example.com", List.of(new SimpleProductDto("Product A", 2)));

        assertThrows(NotFoundException.class, () -> orderService.newOrder(orderDto));
    }

    @Test
    void newOrder_ShouldThrowException_WhenFeingNotConnect() {
        when(clientsResource.findByEmail("test@example.com")).thenThrow(FeignException.class);

        OrderDto orderDto = new OrderDto("test@example.com", List.of(new SimpleProductDto("Product A", 2)));

        assertThrows(FeignException.class, () -> orderService.newOrder(orderDto));
    }

    @Test
    void findById_ShouldReturnOrder_WhenValidId() {
        ProductOrder productOrder = new ProductOrder("Product A", 2, 100.0);
        Order mockOrder = new Order("test@example.com", List.of(productOrder));
        mockOrder.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        when(configProperties.getClientUrl()).thenReturn("www.localhost:testC");
        when(configProperties.getProductUrl()).thenReturn("www.localhost:testP");

        OrderReturnDto result = orderService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getClient().getEmail());
        assertEquals(200.0, result.getTotal());
    }

    @Test
    void findById_ShouldThrowException_WhenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.findById(1L));
    }

    @Test
    void findAll_ShouldReturnPageOfOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        ProductOrder productOrder = new ProductOrder("Product A", 2, 100.0);
        Order mockOrder = new Order("test@example.com", List.of(productOrder));
        mockOrder.setId(1L);

        Page<Order> mockPage = new PageImpl<>(List.of(mockOrder));
        when(orderRepository.findAll(pageable)).thenReturn(mockPage);

        Page<OrderReturnDto> result = orderService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("test@example.com", result.getContent().get(0).getClient().getEmail());
        assertEquals(200.0, result.getContent().get(0).getTotal());
    }

    @Test
    void findAllByEmail_ShouldReturnPageOfOrders_WhenValidEmail() {
        Pageable pageable = PageRequest.of(0, 10);
        ProductOrder productOrder = new ProductOrder("Product A", 2, 100.0);
        Order mockOrder = new Order("test@example.com", List.of(productOrder));
        mockOrder.setId(1L);

        Page<Order> mockPage = new PageImpl<>(List.of(mockOrder));
        when(orderRepository.findAllByEmail(pageable, "test@example.com")).thenReturn(mockPage);

        Page<OrderReturnDto> result = orderService.findAllByEmail(pageable, "test@example.com");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("test@example.com", result.getContent().get(0).getClient().getEmail());
        assertEquals(200.0, result.getContent().get(0).getTotal());
    }

    @Test
    void findAllByEmail_ShouldThrowException_WhenNoOrdersFound() {
        Pageable pageable = PageRequest.of(0, 10);
        when(orderRepository.findAllByEmail(pageable, "invalid@example.com")).thenReturn(Page.empty());

        assertThrows(BadRequestException.class, () -> orderService.findAllByEmail(pageable, "invalid@example.com"));
    }

}
