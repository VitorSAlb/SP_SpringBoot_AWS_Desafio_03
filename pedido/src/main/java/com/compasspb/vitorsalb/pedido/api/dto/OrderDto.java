package com.compasspb.vitorsalb.pedido.api.dto;

import java.util.List;

public record OrderDto(

        String email,
        List<SimpleProductDto> simpleProductDtoList
) {
}
