package com.compasspb.vitorsalb.pedido.integration;

import com.compasspb.vitorsalb.pedido.api.dto.ClientDto;
import com.compasspb.vitorsalb.pedido.api.dto.OrderDto;
import com.compasspb.vitorsalb.pedido.api.dto.ProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.SimpleProductDto;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.ClientReturnDto;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.OrderReturnDto;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.ProductsReturnDto;
import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import com.compasspb.vitorsalb.pedido.domain.entity.ProductOrder;
import com.compasspb.vitorsalb.pedido.domain.repository.OrderRepository;
import com.compasspb.vitorsalb.pedido.domain.service.OrderService;
import com.compasspb.vitorsalb.pedido.infra.clients.ClientsResource;
import com.compasspb.vitorsalb.pedido.infra.clients.ProductResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;




@SpringBootTest
@AutoConfigureMockMvc
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientsResource clientsResource;

    @MockBean
    private ProductResource productResource;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldCreateOrder_AndVerifyCommunicationWithClientsAndProducts() throws Exception {
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("test@example.com");
        when(clientsResource.findByEmail("test@example.com")).thenReturn(ResponseEntity.ok(clientDto));

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Product A");
        productDto.setPrice(100.0);
        productDto.setQuantity(10);
        when(productResource.findByName("Product A")).thenReturn(ResponseEntity.ok(productDto));

        Order mockOrder = new Order("test@example.com", List.of(new ProductOrder("Product A", 2, 100.0)));
        mockOrder.setId(1L);
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        OrderDto orderDto = new OrderDto("test@example.com", List.of(new SimpleProductDto("Product A", 2)));

        ClientReturnDto clientReturnDto = new ClientReturnDto("test@example.com");
        ProductsReturnDto productsReturnDto = new ProductsReturnDto("Product A", 2, 100.0);

        OrderReturnDto orderReturnDto = new OrderReturnDto(1L, clientReturnDto, List.of(productsReturnDto), 200.0);

        when(orderService.newOrder(orderDto)).thenReturn(orderReturnDto);

        mockMvc.perform(post("/ms/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(200.0))
                .andExpect(jsonPath("$.products[0].name").value("Product A"))
                .andExpect(jsonPath("$.products[0].quantity").value(2));
    }
}