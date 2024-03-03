package dev.mas.product.client;

import dev.mas.client.BaseClient;
import dev.mas.product.dto.ProductForConsumerDto;
import dev.mas.product.dto.ProductForConsumerUpdateDto;
import dev.mas.product.filter.ProductFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Map;

@Service
public class ProductClient extends BaseClient {
    private static final String API_PREFIX = "/products";

    protected ProductClient(@Value("${consumer.server.url}") String consumerUrl,
                            RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(consumerUrl
                        + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build());
    }

    public ResponseEntity<Object> postProduct(ProductForConsumerDto productForConsumerDto) {
        return post("", productForConsumerDto);
    }

    public ResponseEntity<Object> getProduct(String path) {
        return get(path);
    }

    public ResponseEntity<Object> putProduct(String path, ProductForConsumerUpdateDto productForConsumerUpdateDto) {
        return put(path, productForConsumerUpdateDto);
    }

    public ResponseEntity<Object> getProductsByCriteria(String name,
                                                        List<Long> categories,
                                                        String description,
                                                        int page,
                                                        int size,
                                                        String byid) {
        Map<String, Object> parameters = Map.of(
                "page", page,
                "size", size,
                "name", name,
                "categories", convertIdListToCommaSeparatedIdsStr(categories),
                "description", description,
                "byid", byid
        );
        return get("?page={page}&size={size}&name={name}&categories={categories}&description={description}&byid={byid}", parameters);
    }

    public ResponseEntity<Object> filterByCriteria(int page,
                                                   int size,
                                                   String byid
            , List<ProductFilter> filters) {
        Map<String, Object> parameters = Map.of(
                "page", page,
                "size", size,
                "byid", byid
        );
        return get("/filtered?page={page}&size={size}&byid={byid}", parameters, filters);
    }

    public ResponseEntity<Object> deleteProduct(String path) {
        return delete(path);
    }

    private String convertIdListToCommaSeparatedIdsStr(List<Long> categories) {
        StringBuilder sb = new StringBuilder();
        for (Long categoryId : categories) {
            sb.append(categoryId);
        }
        return sb.toString();
    }

}
