package com.example.Cart_Service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequest {

    @NotNull
    private Long productId;
    @Min(1)
    private Integer quantity;

}
