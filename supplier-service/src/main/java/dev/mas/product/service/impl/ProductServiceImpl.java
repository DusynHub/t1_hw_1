package dev.mas.product.service.impl;

import dev.mas.category.model.Category;
import dev.mas.category.service.CategoryService;
import dev.mas.exception.ResourceNotFoundException;
import dev.mas.product.model.Product;
import dev.mas.product.model.dto.NewProductDto;
import dev.mas.product.model.dto.ProductDto;
import dev.mas.product.model.dto.ProductMapper;
import dev.mas.product.model.dto.UpdateProductDto;
import dev.mas.product.repository.ProductRepository;
import dev.mas.product.service.ProductService;
import dev.mas.product.service.specification.ProductFilter;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static dev.mas.product.service.specification.ProductSpecification.descriptionFilter;
import static dev.mas.product.service.specification.ProductSpecification.getSpecificationFromFilters;
import static dev.mas.product.service.specification.ProductSpecification.inCategories;
import static dev.mas.product.service.specification.ProductSpecification.nameLike;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;


    @Override
    @Transactional
    public ProductDto saveProduct(NewProductDto newProductDto) {
        log.info("[Product Service] received a consumer service request to save product");
        Product productToSave = productMapper.newProductDtoToProduct(newProductDto);
        Product savedProduct = productRepository.save(productToSave);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, UpdateProductDto updateProductDto) {
        log.info("[Product Service] received a public request to update product with id = {}", id);
        Product productFromDb = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Can't find product with id = %s for update", id))
        );

        if (updateProductDto.getCategory() != null) {
            Category updateCategory = categoryService.getCategoryEntity(updateProductDto.getCategory().getId());
            productFromDb.setCategory(updateCategory);
        }
        productMapper.updateProductFromUpdateProductDto(updateProductDto, productFromDb);
        return productMapper.productToProductDto(productRepository.save(productFromDb));
    }

    @Override
    public ProductDto getProduct(long productId) {
        log.info("[Product Service] received a public request to get product with id = {}", productId);
        return productMapper.productToProductDto(productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Can't find product with id = %s for update", productId))
        ));
    }

    @Override
    public List<ProductDto> findAllByCriteria(String nameLikeFilter, List<Long> categoriesFilter, String descriptionFilter, int pageNumber, int size, String sortById) {
        log.info("[Product Service] received a public request to findAllByCriteria");
        Sort byId = Sort.by(Sort.Direction.ASC, "id");
        if (sortById.equalsIgnoreCase("DESC")) {
            byId = Sort.by(Sort.Direction.DESC);
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, size, byId);

        Specification<Product> filters = Specification.where(StringUtils.isBlank(nameLikeFilter) ? null : nameLike(nameLikeFilter))
                .and(StringUtils.isBlank(descriptionFilter) ? null : descriptionFilter(descriptionFilter))
                .and(CollectionUtils.isEmpty(categoriesFilter) ? null : inCategories(categoriesFilter));

        return productRepository.findAll(filters, pageRequest)
                .stream()
                .map(productMapper::productToProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> filterByCriteria(List<ProductFilter> filters, int pageNumber, int size, String sortById) {
        log.info("[Product Service] received a public request to filter products");
        Sort byId = Sort.by(Sort.Direction.ASC, "id");
        if (sortById.equalsIgnoreCase("DESC")) {
            byId = Sort.by(Sort.Direction.DESC);
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, size, byId);
        if (filters != null && !filters.isEmpty()) {
            return productRepository.findAll(getSpecificationFromFilters(filters), pageRequest).stream()
                    .map(productMapper::productToProductDto)
                    .toList();
        } else {
            return productRepository.findAll(pageRequest).stream()
                    .map(productMapper::productToProductDto)
                    .toList();
        }
    }

    @Override
    public void deleteProduct(long productId) {
        log.info("[Product Service] received a public request to delete product with id = {}", productId);
        productRepository.deleteById(productId);
    }
}
