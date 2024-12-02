package com.compasspb.vitorsalb.pedido.infra.clients;


import com.compasspb.vitorsalb.pedido.api.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "estoque", url = "${custom.product-url}/ms/v1/product")
public interface ProductResource {

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDto> findByName(@PathVariable(value = "name") String name);

    @PutMapping("/remove/{name}/{quantity}")
    public ResponseEntity<ProductDto> removeQuantity(@PathVariable(value = "name") String name, @PathVariable(value = "quantity") Integer quantity);
}
