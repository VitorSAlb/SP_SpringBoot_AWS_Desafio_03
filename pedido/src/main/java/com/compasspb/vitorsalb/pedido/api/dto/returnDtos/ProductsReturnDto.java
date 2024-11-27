package com.compasspb.vitorsalb.pedido.api.dto.returnDtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductsReturnDto extends RepresentationModel<ProductsReturnDto> implements Serializable {

    private String name;
    private Integer quantity;
    private Double price;

    public ProductsReturnDto() {
    }

    public ProductsReturnDto(String name, Integer quantity, Double price) {
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
}
