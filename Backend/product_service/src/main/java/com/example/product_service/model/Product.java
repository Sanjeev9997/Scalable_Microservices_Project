package com.example.product_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please enter name")
    private String name;
    @Size(min=20,message = "Description must be 20 letters")
    private String description;
    @NotNull(message = "Please enter price")
    @Min(value = 1,message = "Price should not negative or zero")
    private Double price;
    @NotBlank(message = "Please enter category")
    private String category;
}
