package com.compasspb.vitorsalb.pedido.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductOrder {

    private String name;
    private Integer quantity;
    private Double price;

    public ProductOrder() {
    }

    public ProductOrder(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ProductOrder(String name, Integer quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return quantity * price;
    }
}
