package com.compasspb.vitorsalb.pedido.infra.clients;


import com.compasspb.vitorsalb.pedido.api.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estoque", url = "http://localhost:8081/ms/v1/product")
public interface ProductResource {

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "name") String name);

    @PatchMapping("/remove/{name}/{quantity}")
    public ResponseEntity<ProductDto> removeQuantity(@PathVariable(value = "name") String name, @PathVariable(value = "quantity") Integer quantity);
}
