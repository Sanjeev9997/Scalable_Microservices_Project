package com.example.product_service.Controller;

import com.example.product_service.Service.ProductService;
import com.example.product_service.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/description")
    public ResponseEntity<List<Product>> getProductBYDescription(@RequestBody String description) {
        return new ResponseEntity<>(productService.findByDescription(description),HttpStatus.OK);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("update")
    public void updateProduct(Product product) {
        productService.updateProduct(product);
    }
}
