package com.compasspb.vitorsalb.pedido.api.dto.returnDtos;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;


public class OrderReturnDto extends RepresentationModel<OrderReturnDto> implements Serializable {

    private Long id;
    private ClientReturnDto client;
    private List<ProductsReturnDto> products;
    private Double total;

    public OrderReturnDto() {
    }

    public OrderReturnDto(Long id, ClientReturnDto client, List<ProductsReturnDto> products, Double total) {
        this.id = id;
        this.client = client;
        this.products = products;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductsReturnDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsReturnDto> products) {
        this.products = products;
    }

    public ClientReturnDto getClient() {
        return client;
    }

    public void setClient(ClientReturnDto client) {
        this.client = client;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
