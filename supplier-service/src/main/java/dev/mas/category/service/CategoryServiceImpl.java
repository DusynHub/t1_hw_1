package dev.mas.category.service;

import dev.mas.category.model.Category;
import dev.mas.category.model.dto.CategoryDto;
import dev.mas.category.model.dto.CategoryMapper;
import dev.mas.category.model.dto.NewCategoryDto;
import dev.mas.category.repository.CategoryRepository;
import dev.mas.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto saveCategory(NewCategoryDto newCategoryDto) {
        log.info("[Category Service] received an request to save category");
        Category categoryToSave = categoryMapper.newCategoryDtoToCategory(newCategoryDto);
        return categoryMapper.categoryToCategoryDto(
                categoryRepository.save(categoryToSave)
        );
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(long id, NewCategoryDto newCategoryDto) {
        log.info("[Category Service] received an request to update category");
        Category categoryToBeUpdated = getCategoryByIdMandatory(id);
        categoryMapper.newCategoryDtoToCategory(newCategoryDto, categoryToBeUpdated);
        return categoryMapper.categoryToCategoryDto(
                categoryRepository.save(categoryToBeUpdated)
        );
    }

    @Override
    @Transactional
    public void deleteCategory(long categoryId) {
        log.info("[Category Service] received an request to delete category");
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException(
                    String.format("User with id = '%d' not found", categoryId)
            );
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> getAllCategories(int pageNumber, int size) {
        log.info("[Category Service] received a public request to get all categories");
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        return categoryRepository.findAllBy(pageRequest)
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        log.info("[Category Service] received a public request to get category with id = '{}'", categoryId);
        return categoryMapper.categoryToCategoryDto(
                getCategoryByIdMandatory(categoryId)
        );
    }

    @Override
    public Category getCategoryEntity(long categoryId) {
        log.info("[Category Service] received a request to get category entity with id = '{}'", categoryId);
        return getCategoryByIdMandatory(categoryId);
    }

    private Category getCategoryByIdMandatory(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Category with id = '%d' not found", id)
                )
        );
    }
}
