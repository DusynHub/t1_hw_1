package dev.mas.product.controller;

import dev.mas.product.model.dto.NewProductDto;
import dev.mas.product.model.dto.ProductDto;
import dev.mas.product.model.dto.UpdateProductDto;
import dev.mas.product.service.ProductService;
import dev.mas.product.service.specification.ProductFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class PublicProductsController {


    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto postProduct(@RequestBody NewProductDto newProductDto) {
        log.info("[Public Controller] received a request POST /products with name {}",
                newProductDto.getName());
        return productService.saveProduct(newProductDto);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto putProduct(@PathVariable long productId,
                                 @RequestBody UpdateProductDto updateProductDto) {
        log.info("[Public Controller] received a request PUT /products/{}", productId);
        return productService.updateProduct(productId, updateProductDto);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable long productId) {
        log.info("[Public Controller] received a request GET /products/{}", productId);
        return productService.getProduct(productId);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) List<Long> categories,
                                    @RequestParam(required = false) String description,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "ASC") String byid) {
        log.info("[Public Controller] received a request GET /products with params");
        return productService.findAllByCriteria(name, categories, description, page, size, byid);
    }

    @GetMapping("/filtered")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> filterAll(@RequestBody(required = false) List<ProductFilter> filters,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "ASC") String byid) {
        log.info("[Public Controller] received a request GET /products/filtered");
        return productService.filterByCriteria(filters, page, size, byid);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long productId) {
        log.info("[Public Controller] received a request DELETE /products/{}", productId);
        productService.deleteProduct(productId);
    }
}
