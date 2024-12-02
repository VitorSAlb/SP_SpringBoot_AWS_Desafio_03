package com.compasspb.vitorsalb.pedido.api.controller;

import com.compasspb.vitorsalb.pedido.api.dto.OrderDto;
import com.compasspb.vitorsalb.pedido.api.dto.PageableDto;
import com.compasspb.vitorsalb.pedido.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.pedido.api.dto.returnDtos.OrderReturnDto;
import com.compasspb.vitorsalb.pedido.domain.entity.Order;
import com.compasspb.vitorsalb.pedido.domain.service.OrderService;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Orders", description = "Endpoints for managing customer requests for products")
@RequestMapping("ms/v1/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    @Operation(summary = "Finds all Orders", description = "Finds all Orders",
            tags = {"Orders"},
            parameters = {
                    @Parameter(name = "size", description = "Number of items per page", example = "5", in = ParameterIn.QUERY),
                    @Parameter(name = "page", description = "Page number (zero-based index)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default is 'id,asc'", example = "id", in = ParameterIn.QUERY)
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
    public ResponseEntity<PageableDto> findAll(@Parameter(hidden = true) @PageableDefault(size = 5, page = 0, sort = {"id"}) Pageable pageable) {
        Page<OrderReturnDto> products = service.findAll(pageable);
        return ResponseEntity.ok(Mapper.pageableToDto(products, OrderReturnDto.class));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Finds all orders by email", description = "Finds all orders by email",
            tags = {"Orders"},
            parameters = {
                    @Parameter(name = "size", description = "Number of items per page", example = "5", in = ParameterIn.QUERY),
                    @Parameter(name = "page", description = "Page number (zero-based index)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default is 'id,asc'", example = "id", in = ParameterIn.QUERY)
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
    public ResponseEntity<PageableDto> findAllByEmail(@Parameter(hidden = true) @PageableDefault(size = 5, page = 0, sort = {"id"}) Pageable pageable, @PathVariable(value = "email") String email) {
        Page<OrderReturnDto> products = service.findAllByEmail(pageable, email);
        return ResponseEntity.ok(Mapper.pageableToDto(products, OrderReturnDto.class));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Finds by Id", description = "Finds by Id",
            tags = {"Orders"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
                    ),@ApiResponse(description = "No Content", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),
            }
    )
    public ResponseEntity<OrderReturnDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Adds a new Order", description = "Adds a new Order",
            tags = {"Orders"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Not Found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),@ApiResponse(description = "Internal Error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = OrderReturnDto.class))
            ),
            }
    )
    public ResponseEntity<OrderReturnDto> create(@RequestBody @Valid OrderDto order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newOrder(order));
    }


}
