package dev.mas.product.service;

import dev.mas.product.model.dto.NewProductDto;
import dev.mas.product.model.dto.ProductDto;
import dev.mas.product.model.dto.UpdateProductDto;
import dev.mas.product.service.specification.ProductFilter;

import java.util.List;

public interface ProductService {

    /**
     * Method to save new
     *
     * @param newProductDto new product to be saved
     * @return saved product
     */
    ProductDto saveProduct(NewProductDto newProductDto);

    /**
     * Method to update product
     *
     * @param updateProductDto that contains product to update
     * @return updated product
     */
    ProductDto updateProduct(Long id, UpdateProductDto updateProductDto);


    /**
     * Method to delete product by id
     *
     * @param id product id to be deleted
     */
    void deleteProduct(long id);


    /**
     * Method to get product by id
     *
     * @param productId required product id
     * @return required product dto
     */
    ProductDto getProduct(long productId);


    /**
     * Method to find products by parameters
     *
     * @param nameLike string that name contains
     * @param categories list of categories id
     * @param descriptionPart string that description contains
     * @param pageNumber page number
     * @param size page size
     * @param sortById sort type by id
     * @return list of products
     */
    List<ProductDto> findAllByCriteria(String nameLike, List<Long> categories, String descriptionPart, int pageNumber, int size, String sortById);


    /**
     * Method to filter products by filters
     *
     * @param filters filters list
     * @param pageNumber page number
     * @param size page size
     * @param sortById sort type by id
     * @return list of filtered products
     */
    List<ProductDto> filterByCriteria(List<ProductFilter> filters, int pageNumber, int size, String sortById);
}
