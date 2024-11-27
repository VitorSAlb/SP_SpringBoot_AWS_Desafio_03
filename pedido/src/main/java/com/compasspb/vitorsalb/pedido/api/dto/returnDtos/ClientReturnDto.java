package com.compasspb.vitorsalb.pedido.api.dto.returnDtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientReturnDto extends RepresentationModel<ClientReturnDto> implements Serializable {

    private String email;

    public ClientReturnDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
