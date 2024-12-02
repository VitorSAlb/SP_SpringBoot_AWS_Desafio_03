package com.compasspb.vitorsalb.estoque.api.controller;

import com.compasspb.vitorsalb.estoque.api.dto.PageableDto;
import com.compasspb.vitorsalb.estoque.api.dto.ProductDto;
import com.compasspb.vitorsalb.estoque.api.dto.mapper.Mapper;
import com.compasspb.vitorsalb.estoque.domain.service.ProductService;
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
@Tag(name = "Inventory", description = "Endpoints for Managing Products of inventory")
@RequestMapping("ms/v1/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    @Operation(summary = "Finds all Products", description = "Finds all Products",
        tags = {"Inventory"},
            parameters = {
                    @Parameter(name = "size", description = "Number of items per page", example = "5", in = ParameterIn.QUERY),
                    @Parameter(name = "page", description = "Page number (zero-based index)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default is 'name,asc'", example = "name", in = ParameterIn.QUERY)
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
    public ResponseEntity<PageableDto> findAll(@Parameter(hidden = true) @PageableDefault(size = 5, page = 0, sort = {"name"}) Pageable pageable) {
        Page<ProductDto> products = service.findAll(pageable);
        return ResponseEntity.ok(Mapper.pageableToDto(products, ProductDto.class));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Finds by Id", description = "Finds by Id",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "No Content", responseCode = "204",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
            }
    )
    public ResponseEntity<ProductDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Finds by Email", description = "Finds by Email",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "No Content", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
            }
    )
    public ResponseEntity<ProductDto> findByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping
    @Operation(summary = "Adds a new Product", description = "Adds a new Product",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
            }
    )
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/add/{name}/{quantity}")
    @Operation(summary = "Increase the quantity of products in the inventory", description = "Increase the quantity of products in the inventory",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
            }
    )
    public ResponseEntity<ProductDto> addQuantity(@PathVariable(value = "name") String name, @PathVariable(value = "quantity") Integer quantity) {
        return ResponseEntity.ok(service.addProduct(name, quantity));
    }

    @PutMapping("/remove/{name}/{quantity}")
    @Operation(summary = "Decrease the quantity of products in the inventory", description = "Decrease the quantity of products in the inventory",
            tags = {"Inventory"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Unauthorized", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Not Found", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),@ApiResponse(description = "Internal Error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
            }
    )
    public ResponseEntity<ProductDto> removeQuantity(@PathVariable(value = "name") String name, @PathVariable(value = "quantity") Integer quantity) {
        return ResponseEntity.ok(service.removeProduct(name, quantity));
    }
}
