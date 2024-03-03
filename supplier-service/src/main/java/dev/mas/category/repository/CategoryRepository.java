package dev.mas.category.repository;

import dev.mas.category.model.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    /**
     * Method to get all categories by pageRequest
     *
     * @param pageRequest page from and page size in pageRequestForm
     * @return required categories
     */
    List<Category> findAllBy(PageRequest pageRequest);
}
