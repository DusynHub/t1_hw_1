package dev.mas.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForConsumerDto {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 1, message = "Name length is smaller than 1")
    @Size(max = 50, message = "Name length is bigger than 50")
    private String name;
}