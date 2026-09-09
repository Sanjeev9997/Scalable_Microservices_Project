package com.example.product_service.Service;

import com.example.product_service.Repository.ProductRepository;
import com.example.product_service.model.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger log= LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        log.info("Finding all products");
        List<Product> products=productRepository.findAll();
        log.info("Products found");
        return products;
    }

    @Cacheable(value = "products",key="#id")
    @Override
    public Product findById(Long id) {
        log.info("Finding product with id={}", id);
        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        log.info("Product found");
        return product;
    }

    @Cacheable(value = "products",key="#id")
    @Override
    public List<Product> findByName(String name) {
        log.info("Finding product with name={}", name);
        List<Product> products=productRepository.findByName(name);
        log.info("Products found");
        return products;
    }

    @Cacheable(value = "description",key = "#description")
    @Override
    public List<Product> findByDescription(String description) {
        log.info("Finding product with description={}", description);
        List<Product> products=productRepository.findByDescription(description);
        log.info("Products found");
        return products;
    }


    @Override
    public void addProduct(Product product)
    {
        log.info("Adding product {}", product);
        productRepository.save(product);
        log.info("Product added");
    }

    @CacheEvict(value = "products",key="#id")
    @Override
    public void deleteProduct(long id) {
        log.info("Deleting product {}", id);
        log.info("Checking product..");
       Product pro=findById(id);
       if(pro!=null){
           log.info("Product found");
           productRepository.deleteById(id);
           log.info("Product deleted");
       }
       else{
           log.info("Product not found");
       }


    }

    @CachePut(value = "products",key="#product.id")
    @Override
    public void updateProduct(Product product) {
        log.info("Updating product {}", product);
        log.info("Checking product..");
        Product pro=findById(product.getId());
        if(pro!=null) {
            log.info("Product found");
            productRepository.save(product);
            log.info("Product deleted");
        }
        else{
           log.info("Product not found");
        }
    }
}
