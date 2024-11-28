package com.compasspb.vitorsalb.pedido.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderDto(

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be a valid format.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,
        @NotEmpty(message = "Any product is required to made a new order")
        List<SimpleProductDto> products
) {
}
