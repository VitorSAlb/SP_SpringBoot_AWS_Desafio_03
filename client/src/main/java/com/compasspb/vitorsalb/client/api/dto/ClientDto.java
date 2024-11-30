package com.compasspb.vitorsalb.client.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ClientDto extends RepresentationModel<ClientDto> implements Serializable {

    private Long id;
    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^[A-Z].*", message = "First name must start with a capital letter.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^[A-Z].*", message = "The last name must start with a capital letter.")
    private String lastName;
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid format.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;
    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Integer totalOrders;

    public ClientDto() {
    }

    public ClientDto(String firstName, String lastName, String email, LocalDate birthday, Integer totalOrders) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.totalOrders = totalOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(email, clientDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
}
