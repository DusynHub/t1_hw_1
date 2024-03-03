package dev.mas.product.service.specification;

import dev.mas.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> nameLike(String nameLike) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + nameLike + "%");
    }

    public static Specification<Product> inCategories(List<Long> categoriesId) {
        return (root, query, builder) -> root.get("category").get("id")
                .in(categoriesId);
    }

    public static Specification<Product> descriptionFilter(String stringInDescription) {
        return (root, query, builder) -> builder.like(root.get("description"), "%" + stringInDescription + "%");
    }

    private static Specification<Product> createSpecificationFromFilters(ProductFilter input) {

        return switch (input.getOperator()) {
            case EQUALS -> (root, query, criteriaBuilder) -> {
                if (input.getField().equals("category.name")) {
                    return criteriaBuilder.equal(root.get("category").get("name"),
                            castToRequiredType(root.get("category").get("name").getJavaType(),
                                    input.getValue()));
                } else if (input.getField().equals("category.id")) {
                    return criteriaBuilder.equal(root.get("category").get("id"),
                            castToRequiredType(root.get("category").get("id").getJavaType(),
                                    input.getValue()));
                } else {
                    return criteriaBuilder.equal(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
                }
            };
            case NOT_EQUALS -> (root, query, criteriaBuilder) -> {
                if (input.getField().equals("category.name")) {
                    return criteriaBuilder.notEqual(root.get("category").get("name"),
                            castToRequiredType(root.get("category").get("name").getJavaType(),
                                    input.getValue()));
                } else if (input.getField().equals("category.id")) {
                    return criteriaBuilder.notEqual(root.get("category").get("id"),
                            castToRequiredType(root.get("category").get("id").getJavaType(),
                                    input.getValue()));
                } else {
                    return criteriaBuilder.notEqual(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
                }
            };
            case GREATER_THAN -> (root, query, criteriaBuilder) -> criteriaBuilder.gt(root.get(input.getField()),
                    (Number) castToRequiredType(
                            root.get(input.getField()).getJavaType(),
                            input.getValue()));
            case LESS_THAN -> (root, query, criteriaBuilder) -> criteriaBuilder.lt(root.get(input.getField()),
                    (Number) castToRequiredType(
                            root.get(input.getField()).getJavaType(),
                            input.getValue()));
            case LIKE -> (root, query, criteriaBuilder) -> {
                if (input.getField().equals("category.name")) {
                    return criteriaBuilder.like(root.get("category").get("name"),
                            "%" + input.getValue() + "%");
                } else {
                    return criteriaBuilder.like(root.get(input.getField()),
                            "%" + input.getValue() + "%");
                }
            };
            case IN -> (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(input.getField()))
                    .value(castToRequiredType(
                            root.get(input.getField()).getJavaType(),
                            input.getValues()));
        };
    }

    private static Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(BigDecimal.class)) {
            return BigDecimal.valueOf(Double.valueOf(value));
        } else if (fieldType.isAssignableFrom(String.class)) {
            return value;
        } else if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        }
        return null;
    }

    private static Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

    public static Specification<Product> getSpecificationFromFilters(List<ProductFilter> productFilter) {
        Specification<Product> specification =
                where(createSpecificationFromFilters(productFilter.remove(0)));
        for (ProductFilter inputFilter : productFilter) {
            specification = specification.and(createSpecificationFromFilters(inputFilter));
        }
        return specification;
    }
}
