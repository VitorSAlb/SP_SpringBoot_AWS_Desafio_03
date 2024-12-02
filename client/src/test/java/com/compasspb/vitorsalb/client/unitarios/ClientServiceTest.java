package com.compasspb.vitorsalb.client.unitarios;

import com.compasspb.vitorsalb.client.api.dto.ClientDto;
import com.compasspb.vitorsalb.client.domain.entity.Client;
import com.compasspb.vitorsalb.client.domain.repository.ClientRepository;
import com.compasspb.vitorsalb.client.domain.service.ClientService;
import com.compasspb.vitorsalb.client.infra.exceptions.DuplicateException;
import com.compasspb.vitorsalb.client.infra.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ClientServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private ClientRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newClient_shouldCreateClient_WhenValidInput() {
        ClientDto clientDto = new ClientDto("t1", "t1", "teste1@example.com", LocalDate.now());
        Client client = new Client(1L, "t1", "t1", "teste1@example.com", LocalDate.now());

        when(repository.existsByEmail(clientDto.getEmail())).thenReturn(false);
        when(repository.save(any(Client.class))).thenReturn(client);

        ClientDto result = service.create(clientDto);

        assertNotNull(result);
        assertEquals("t1", result.getFirstName());
        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    void newClient_shouldThrowDuplicateException_WhenEmailExists() {
        ClientDto clientDto = new ClientDto("t1", "t1", "teste1@example.com", LocalDate.now());

        when(repository.existsByEmail(clientDto.getEmail())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> service.create(clientDto));
        verify(repository, never()).save(any(Client.class));
    }
}
