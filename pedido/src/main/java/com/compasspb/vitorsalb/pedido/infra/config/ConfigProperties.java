package com.compasspb.vitorsalb.pedido.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Value("${custom.client-url}")
    private String clientUrl;

    @Value("${custom.product-url}")
    private String productUrl;

    public String getClientUrl() {
        return clientUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }
}
