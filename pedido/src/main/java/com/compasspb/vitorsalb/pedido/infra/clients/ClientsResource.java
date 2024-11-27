package com.compasspb.vitorsalb.pedido.infra.clients;

import com.compasspb.vitorsalb.pedido.api.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client", url = "http://localhost:8082/ms/v1/client")
public interface ClientsResource {

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDto> findByEmail(@PathVariable(value = "email") String email);
}
