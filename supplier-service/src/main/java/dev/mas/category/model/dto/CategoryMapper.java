package dev.mas.category.model.dto;

import dev.mas.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for Category
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToCategoryDto(Category category);

    Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto);

    void newCategoryDtoToCategory(NewCategoryDto newCategoryDto,
                                  @MappingTarget Category categoryToBeUpdated);

}