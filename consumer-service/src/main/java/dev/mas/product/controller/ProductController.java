package dev.mas.product.controller;

import dev.mas.product.client.ProductClient;
import dev.mas.product.dto.ProductForConsumerDto;
import dev.mas.product.dto.ProductForConsumerUpdateDto;
import dev.mas.product.filter.ProductFilter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(path = "consumer/products")
@Validated
public class ProductController {

    private final ProductClient productClient;

    /**
     * Method to post product
     *
     * @param productForConsumerDto product to add
     * @return saved product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postProduct(@Valid @RequestBody ProductForConsumerDto productForConsumerDto) {
        log.info("[Product Controller] received a request POST /products with name {}",
                productForConsumerDto.getName());
        return productClient.postProduct(productForConsumerDto);
    }


    /**
     * Method to update product
     *
     * @param productId                   product id to update
     * @param productForConsumerUpdateDto required body with new values
     * @return updated product
     */
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> putProduct(@Valid @PathVariable @Positive long productId,
                                             @Valid @RequestBody ProductForConsumerUpdateDto productForConsumerUpdateDto) {
        log.info("[Product Controller] received a request PUT /products/{}", productId);
        return productClient.putProduct(String.format("/%s", productId), productForConsumerUpdateDto);
    }


    /**
     * Method to get product by id
     *
     * @param productId product id to get
     * @return required product
     */
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getProduct(@Valid @PathVariable @Positive long productId) {
        log.info("[Product Controller] received a request GET /products/{}", productId);
        return productClient.getProduct(String.format("/%s", productId));
    }


    /**
     * Method to find products by criteria product
     *
     * @param name        string which name should contain
     * @param categories  list of categories id
     * @param description string which description should contain
     * @param page        required page
     * @param size        required page size
     * @param byid        sort order by id
     * @return products
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findAllByCriteria(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                    @RequestParam(defaultValue = "10") @PositiveOrZero int size,
                                                    @RequestParam(defaultValue = "ASC") String byid) {
        log.info("[Product Controller] received a request GET /products with params");
        return productClient.getProductsByCriteria(name, categories, description, page, size, byid);
    }

    /**
     * Method to filter products by complex filters
     *
     * @param filters list of filters
     * @param page    required page
     * @param size    required page size
     * @param byid    sort order by id
     * @return filtered products
     */
    @GetMapping("/filtered")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> filterAll(@RequestBody(required = false) List<ProductFilter> filters,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                            @RequestParam(defaultValue = "10") @PositiveOrZero int size,
                                            @RequestParam(defaultValue = "ASC") String byid) {
        log.info("[Product Controller] received a request GET /products/filtered");
        return productClient.filterByCriteria(page, size, byid, filters);
    }

    /**
     * Method to delete product by id
     *
     * @param productId product id to delete
     * @return status code
     */
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteProductById(@PathVariable @Positive long productId) {
        log.info("[Product Controller] received a request DELETE /categories/{}", productId);
        return productClient.deleteProduct(String.format("/%s", productId));
    }
}
