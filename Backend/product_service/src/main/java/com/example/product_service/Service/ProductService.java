package com.example.product_service.Service;

import com.example.product_service.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> findAll();
    public Product findById(Long id);
    public List<Product> findByName(String name);
    public List<Product> findByDescription(String description);
    public void addProduct(Product product);
    public void deleteProduct(long id);
    public void updateProduct(Product product);
}
