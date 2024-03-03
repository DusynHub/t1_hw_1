package dev.mas.product.model.dto;

import dev.mas.category.model.dto.CategoryDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {

    @Size(max = 255, message = "Max product name size is 255 symbols")
    private String name;

    @Size(max = 1000, message = "Max product description size is 1000 symbols")
    private String description;

    @Positive(message = "Price must be positive number")
    private BigDecimal price;

    private CategoryDto category;
}
