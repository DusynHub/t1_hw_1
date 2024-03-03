package dev.mas.product.dto;

import dev.mas.category.dto.CategoryForProductDto;
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
public class ProductForConsumerUpdateDto {


    @Size(max = 255, message = "Max product name size is 255 symbols")
    private String name;

    @Size(max = 1000, message = "Max product description size is 1000 symbols")
    private String description;

    @Positive(message = "Price must be positive number")
    private BigDecimal price;

    private CategoryForProductDto category;
}
