package com.compasspb.vitorsalb.pedido.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Value("${custom.client-url}")
    private String clientUrl;

    @Value("${custom.product-url}")
    private String productUrl;

    @Value("${custom.external-client-url}")
    private String externalClientUrl;

    @Value("${custom.external-product-url}")
    private String externalProductUrl;

    public String getClientUrl() {
        return clientUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getExternalClientUrl() {
        return externalClientUrl;
    }

    public String getExternalProductUrl() {
        return externalProductUrl;
    }
}
