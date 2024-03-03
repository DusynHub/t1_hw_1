package dev.mas.category.controller;

import dev.mas.category.model.dto.CategoryDto;
import dev.mas.category.model.dto.NewCategoryDto;
import dev.mas.category.service.CategoryService;
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
@RequestMapping(path = "/categories")
public class PublicCategoriesController {

    private final CategoryService categoryService;

    /**
     * Method to add new category
     *
     * @param newCategoryDto new category
     * @return added new category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto postCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("[Public Controller] received a request POST /categories with name {}",
                newCategoryDto.getName());
        return categoryService.saveCategory(newCategoryDto);
    }

    /**
     * Method to get all categories by page
     *
     * @param page page number stating from 0 [defaultValue = "0"]
     * @param size page size [defaultValue = "10"]
     * @return page of categories
     */
    @GetMapping
    List<CategoryDto> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("[Public Controller] received a request GET /categories");
        return categoryService.getAllCategories(page, size);
    }

    /**
     * Method to get category by id
     *
     * @param categoryId id
     * @return requested category
     */
    @GetMapping("/{categoryId}")
    CategoryDto getCategory(@PathVariable long categoryId) {
        log.info("[Public Controller] received a request GET /categories");
        return categoryService.getCategory(categoryId);
    }

    /**
     * Method to delete category by id
     *
     * @param categoryId category to be deleted
     */
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long categoryId) {
        log.info("[Public Controller] received a request DELETE /categories/{}", categoryId);
        categoryService.deleteCategory(categoryId);
    }

    /**
     * Method to update category
     *
     * @param categoryId     category to update
     * @param newCategoryDto category fields with new values
     * @return updated category
     */
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto putCategory(
            @PathVariable long categoryId,
            @RequestBody NewCategoryDto newCategoryDto) {
        log.info("[Public Controller] received a request PUT /categories/{}", categoryId);
        return categoryService.updateCategory(categoryId, newCategoryDto);
    }
}