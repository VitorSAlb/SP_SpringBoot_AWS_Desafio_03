package com.compasspb.vitorsalb.estoque;

import com.compasspb.vitorsalb.estoque.api.dto.ProductDto;
import com.compasspb.vitorsalb.estoque.domain.entity.Product;
import com.compasspb.vitorsalb.estoque.domain.repository.ProductRepository;
import com.compasspb.vitorsalb.estoque.domain.service.ProductService;
import com.compasspb.vitorsalb.estoque.infra.exceptions.BadRequestException;
import com.compasspb.vitorsalb.estoque.infra.exceptions.DuplicateException;
import com.compasspb.vitorsalb.estoque.infra.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Save_ShouldThrowDuplicateException_WhenProductExists() {
        ProductDto productDto = new ProductDto();
        productDto.setName("test-product");

        when(productRepository.existsByName("test-product")).thenReturn(true);

        assertThrows(DuplicateException.class, () -> productService.save(productDto));
        verify(productRepository, times(1)).existsByName("test-product");
    }

    @Test
    void Save_ShouldSaveProduct_WhenProductDoesNotExist() {
        ProductDto productDto = new ProductDto();
        productDto.setName("new-product");

        Product product = new Product();
        product.setId(1L);

        when(productRepository.existsByName("new-product")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.save(productDto);

        assertNotNull(result);
        verify(productRepository, times(1)).existsByName("new-product");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void FindAll_ShouldReturnPagedProducts() {
        Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        Page<Product> productPage = new PageImpl<>(List.of(product1, product2));
        when(productRepository.findAllp(PageRequest.of(0, 10))).thenReturn(productPage);

        Page<ProductDto> result = productService.findAll(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        verify(productRepository, times(1)).findAllp(any(PageRequest.class));
    }

    @Test
    void FindById_ShouldThrowNotFoundException_WhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void FindById_ShouldReturnProduct_WhenProductExists() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto result = productService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void FindByName_ShouldThrowNotFoundException_WhenProductNotFound() {
        when(productRepository.findByName("test")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.findByName("test"));
        verify(productRepository, times(1)).findByName("test");
    }

    @Test
    void FindByName_ShouldReturnProduct_WhenProductExists() {
        Product product = new Product();
        product.setName("test");

        when(productRepository.findByName("test")).thenReturn(Optional.of(product));

        ProductDto result = productService.findByName("test");

        assertNotNull(result);
        assertEquals("test", result.getName());
        verify(productRepository, times(1)).findByName("test");
    }

    @Test
    void AddProduct_ShouldIncreaseQuantity_WhenProductExists() {
        String productName = "existing-product";
        Integer additionalQuantity = 5;

        ProductDto existingProductDto = new ProductDto();
        existingProductDto.setName(productName);
        existingProductDto.setId(1L);
        existingProductDto.setQuantity(10);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName(productName);
        existingProduct.setQuantity(10);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName(productName);
        updatedProduct.setQuantity(15);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(productRepository.findById(1L)).thenReturn(Optional.of(updatedProduct));

        ProductDto result = productService.addProduct(productName, additionalQuantity);

        assertNotNull(result);
        assertEquals(15, result.getQuantity());
        verify(productRepository, times(1)).findByName(productName);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void AddProduct_ShouldThrowNotFoundException_WhenProductDoesNotExist() {
        String productName = "non-existing-product";
        Integer additionalQuantity = 5;

        when(productRepository.findByName(productName)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.addProduct(productName, additionalQuantity));
        verify(productRepository, times(1)).findByName(productName);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void RemoveProduct_ShouldDecreaseQuantity_WhenSufficientQuantityExists() {
        String productName = "existing-product";
        Integer removalQuantity = 5;

        ProductDto existingProductDto = new ProductDto();
        existingProductDto.setName(productName);
        existingProductDto.setId(1L);
        existingProductDto.setQuantity(10);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName(productName);
        existingProduct.setQuantity(10);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName(productName);
        updatedProduct.setQuantity(5);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(productRepository.findById(1L)).thenReturn(Optional.of(updatedProduct));

        ProductDto result = productService.removeProduct(productName, removalQuantity);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(productRepository, times(1)).findByName(productName);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void RemoveProduct_ShouldThrowBadRequestException_WhenInsufficientQuantity() {
        String productName = "existing-product";
        Integer removalQuantity = 15;

        ProductDto existingProductDto = new ProductDto();
        existingProductDto.setName(productName);
        existingProductDto.setId(1L);
        existingProductDto.setQuantity(10);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName(productName);
        existingProduct.setQuantity(10);

        when(productRepository.findByName(productName)).thenReturn(Optional.of(existingProduct));

        assertThrows(BadRequestException.class, () -> productService.removeProduct(productName, removalQuantity));
        verify(productRepository, times(1)).findByName(productName);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void RemoveProduct_ShouldThrowNotFoundException_WhenProductDoesNotExist() {
        String productName = "non-existing-product";
        Integer removalQuantity = 5;

        when(productRepository.findByName(productName)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.removeProduct(productName, removalQuantity));
        verify(productRepository, times(1)).findByName(productName);
        verify(productRepository, never()).save(any(Product.class));
    }

}