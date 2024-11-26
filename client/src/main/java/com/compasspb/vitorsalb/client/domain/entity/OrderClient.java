package com.compasspb.vitorsalb.client.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders_of_client")
public class OrderClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "id_order", nullable = false)
    private Long idOrder;

    public OrderClient() {
    }

    public OrderClient(String email, Long idOrder) {
        this.email = email;
        this.idOrder = idOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }
}
