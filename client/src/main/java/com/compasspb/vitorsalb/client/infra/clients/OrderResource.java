package com.compasspb.vitorsalb.client.infra.clients;


import com.compasspb.vitorsalb.client.api.dto.PageableDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedido", url = "${custom.order-url}/ms/v1/order")
public interface OrderResource {

    @GetMapping("/email/{email}")
    public ResponseEntity<PageableDto> findAllByEmail(@PageableDefault(size = 5, page = 0, sort = {"id"}) Pageable pageable, @PathVariable(value = "email") String email);
}
