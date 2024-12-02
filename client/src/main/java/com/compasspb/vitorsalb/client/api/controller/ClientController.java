package com.compasspb.vitorsalb.client.api.controller;

import com.compasspb.vitorsalb.client.api.dto.ClientDto;
import com.compasspb.vitorsalb.client.api.dto.PageableDto;
import com.compasspb.vitorsalb.client.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.client.domain.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Clients", description = "Endpoints for Managing Client")
@RequestMapping("ms/v1/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    @Operation(summary = "Finds all Clients", description = "Finds all Clients",
            tags = {"Clients"},
            parameters = {
                    @Parameter(name = "size", description = "Number of items per page", example = "5", in = ParameterIn.QUERY),
                    @Parameter(name = "page", description = "Page number (zero-based index)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default is 'firstName,asc'", example = "firstName", in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = { @Content( mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PageableDto.class)))}
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = { @Content( mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PageableDto.class)))}
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = { @Content( mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PageableDto.class)))}
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = { @Content( mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PageableDto.class)))}
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = { @Content( mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PageableDto.class)))}
            ),
            }
    )
    public ResponseEntity<PageableDto> findAll(@Parameter(hidden = true) @PageableDefault(size = 5, page = 0, sort = {"firstName"}) Pageable pageable) {
        Page<ClientDto> products = service.findAll(pageable);
        return ResponseEntity.ok(Mapper.pageableToDto(products, ClientDto.class));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Finds by Id", description = "Finds by Id",
            tags = {"Clients"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ClientDto.class))
                    ),@ApiResponse(description = "No Content", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),
            }
    )
    public ResponseEntity<ClientDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Finds by Email", description = "Finds by Email",
            tags = {"Clients"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ClientDto.class))
                    ),@ApiResponse(description = "No Content", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),
            }
    )
    public ResponseEntity<ClientDto> findByEmail(@PathVariable(value = "email") String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PostMapping
    @Operation(summary = "Adds a new Client", description = "Adds a new Client",
            tags = {"Clients"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ClientDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),
            }
    )
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/update/{email}")
    @Operation(summary = "Update a Client", description = "Update a Client",
            tags = {"Clients"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ClientDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))
            ),
            }
    )
    public ResponseEntity<ClientDto> update(@PathVariable(value = "email") String email, @RequestBody @Valid ClientDto dto) {
        return ResponseEntity.ok(service.update(dto, email));
    }

}
