package dev.mas.product.service.specification;

import dev.mas.util.OperatorQuery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductFilter {
    private String field;
    private OperatorQuery operator;
    private String value;
    private List<String> values;
}
