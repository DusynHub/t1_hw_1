package dev.mas.category.service;


import dev.mas.category.model.Category;
import dev.mas.category.model.dto.CategoryDto;
import dev.mas.category.model.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * Method to save new category
     *
     * @param newCategoryDto new category to be saved
     * @return saved category
     */
    CategoryDto saveCategory(NewCategoryDto newCategoryDto);

    /**
     * Method to update category name by id
     *
     * @param id             category id to be patched
     * @param newCategoryDto that contains new category name
     * @return patched category
     */
    CategoryDto updateCategory(long id, NewCategoryDto newCategoryDto);


    /**
     * Method to delete category by id
     *
     * @param id category id to be deleted
     */
    void deleteCategory(long id);

    /**
     * Method to get all categories
     *
     * @param fromLine first category in list
     * @param size     page size
     * @return required category list
     */
    List<CategoryDto> getAllCategories(int fromLine, int size);


    /**
     * Method to get category by id
     *
     * @param categoryId required category id
     * @return required category dto
     */
    CategoryDto getCategory(long categoryId);


    /**
     * Method to get category by id
     *
     * @param categoryId required category id
     * @return required category
     */
    Category getCategoryEntity(long categoryId);


}
