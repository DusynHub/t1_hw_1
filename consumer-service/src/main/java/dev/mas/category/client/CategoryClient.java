package dev.mas.category.client;

import dev.mas.category.dto.CategoryForConsumerDto;
import dev.mas.client.BaseClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

/**
 * Client class that sends requests to Suppliers service
 */
@Service
public class CategoryClient extends BaseClient {

    private static final String API_PREFIX = "/categories";

    protected CategoryClient(@Value("${consumer.server.url}") String consumerUrl,
                             RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(consumerUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build());
    }

    /**
     * Method sends post category request to supplier service
     */
    public ResponseEntity<Object> postCategory(CategoryForConsumerDto categoryForConsumerDto) {
        return post("", categoryForConsumerDto);
    }

    /**
     * Method sends get category request to supplier service
     */
    public ResponseEntity<Object> getCategory(String path) {
        return get(path);
    }

    /**
     * Method sends getAllCategoriesByPage request to supplier service
     */
    public ResponseEntity<Object> getAllCategoriesByPage(int page, int size) {
        Map<String, Object> parameters = Map.of(
                "page", page,
                "size", size
        );
        return get("?page={page}&size={size}", parameters);
    }

    /**
     * Method sends putCategory request to supplier service
     */
    public ResponseEntity<Object> putCategory(String path, CategoryForConsumerDto categoryForConsumerDto) {
        return put(path, categoryForConsumerDto);
    }

    /**
     * Method sends delete request to category by path
     */
    public ResponseEntity<Object> deleteCategory(String path) {
        return delete(path);
    }
}
