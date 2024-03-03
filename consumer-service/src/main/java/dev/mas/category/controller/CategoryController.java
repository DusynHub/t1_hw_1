package dev.mas.category.controller;

import dev.mas.category.client.CategoryClient;
import dev.mas.category.dto.CategoryForConsumerDto;
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

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "consumer/categories")
@Validated
public class CategoryController {

    private final CategoryClient categoryClient;

    /**
     * Method to add new category
     *
     * @param categoryForConsumerDto new category
     * @return added new category
     */
    @PostMapping
    public ResponseEntity<Object> postCategory(@RequestBody @Valid CategoryForConsumerDto categoryForConsumerDto) {
        log.info("[Consumer controller]: Получен запрос POST consumer/categories");
        return categoryClient.postCategory(categoryForConsumerDto);
    }

    /**
     * Method to get category by id
     *
     * @param categoryId id
     * @return requested category
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable @Positive long categoryId) {
        log.info("[Consumer controller]: Получен запрос GET consumer/categories/{}", categoryId);
        return categoryClient.getCategory(String.format("/%s", categoryId));
    }

    /**
     * Method to get all categories by page
     *
     * @param page page number stating from 0 [defaultValue = "0"]
     * @param size page size [defaultValue = "10"]
     * @return page of categories
     */
    @GetMapping
    ResponseEntity<Object> getAllCategories(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("[Consumer Controller] received a request GET consumer/categories");
        return categoryClient.getAllCategoriesByPage(page, size);
    }

    /**
     * Method to update category
     *
     * @param categoryId             category to update
     * @param categoryForConsumerDto category fields with new values. If field is absent entity value would not be changed
     * @return updated category
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable @Positive long categoryId,
            @RequestBody @Valid CategoryForConsumerDto categoryForConsumerDto) {
        log.info("[Consumer controller]: Получен запрос PUT consumer/categories/{}", categoryId);
        return categoryClient.putCategory(String.format("/%s", categoryId), categoryForConsumerDto);
    }

    /**
     * Method to delete category by id
     *
     * @param categoryId category to be deleted
     * @return 204 status
     */
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteCategoryById(@PathVariable @Positive long categoryId) {
        log.info("[Consumer Controller] received a request DELETE consumer/categories/{}", categoryId);
        return categoryClient.deleteCategory(String.format("/%s", categoryId));
    }

}

