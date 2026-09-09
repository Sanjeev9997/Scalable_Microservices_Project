package com.example.inventory_service.service;

import com.example.inventory_service.client.ProductClient;
import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.ProductResponse;
import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private static final Logger log= LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final InventoryRepository inventoryRepository;
//    private final ProductClient productClient;
    private final ProductClientService productClientService;
    @Override
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        log.info("createInventory called");
        log.info("Fetching product");
        ProductResponse product = productClientService.getProductById(inventoryRequest.getProductId());

        if(product == null){
            throw new RuntimeException("Product not found");
        }
        log.info("product fetched");
        Inventory inventory =Inventory.builder().
                productId(inventoryRequest.getProductId()).
                quantity(inventoryRequest.getQuantity())
                .availableQuantity(inventoryRequest.getQuantity())
                .reservedQuantity(0)
                .build();
        inventoryRepository.save(inventory);

        log.info("createInventory completed");
        return mapToResponse(inventory);

    }

    @Override
    public InventoryResponse getInventory(Long productId) {
        log.info("getInventory called");
        log.info("Fetching product inventory");
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Product not found"));
        log.info("Inventory fetched");
        log.info("getInventory completed");
        return mapToResponse(inventory);
    }

    @Override
    public boolean isInStock(Long productId, Integer quantity) {
        log.info("isInStock called");
        log.info("Fetching product inventory");
        Inventory inventory=inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Product not found"));
        log.info("Product inventory fetched");
        log.info("isInStock completed");
        return inventory.getAvailableQuantity()>=quantity;
    }

    @Override
    public void reserveStock(Long productId, Integer quantity) {
        log.info("reserveStock called");
        log.info("Fetching product inventory");
       Inventory inventory=inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Product not found"));
       log.info("Product inventory fetched");
        if (inventory.getAvailableQuantity() < quantity) {
            log.info("Stock is less");
            throw new RuntimeException("Insufficient stock");
        }

       inventory.setAvailableQuantity(inventory.getAvailableQuantity()-quantity);
       inventory.setReservedQuantity(inventory.getReservedQuantity()+quantity);
       inventoryRepository.save(inventory);
       log.info("reserveStock completed");
    }

    @Override
    public void releaseStock(Long productId, Integer quantity) {
      log.info("releaseStock called");
      log.info("Fetching product inventory");
      Inventory inventory=inventoryRepository.findByProductId(productId).orElseThrow(()-> new RuntimeException("Product not found"));
      log.info("Product inventory fetched");
      inventory.setAvailableQuantity(inventory.getAvailableQuantity()+quantity);
      inventory.setReservedQuantity(inventory.getReservedQuantity()-quantity);
      inventoryRepository.save(inventory);
      log.info("releaseStock completed");
    }

    @Override
    public void deductStock(Long productId, Integer quantity) {
        log.info("deductStock called");
        log.info("Fetching product inventory");
        Inventory inventory=inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Product not found"));
        log.info("Product inventory fetched");
        inventory.setQuantity(inventory.getQuantity()-quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity()-quantity);
        inventoryRepository.save(inventory);
        log.info("deductStock completed");
    }

    @Override
    public void addStock(Long productId, Integer quantity) {
        log.info("addStock called");
        log.info("Fetching product inventory");
        Inventory inventory=inventoryRepository.findByProductId(productId).orElseThrow(()->new RuntimeException("Product not found"));
        log.info("Product inventory fetched");
        inventory.setQuantity(inventory.getQuantity()+quantity);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity()+quantity);
        inventoryRepository.save(inventory);
        log.info("addStock completed");
    }
    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder().
                productId(inventory.getProductId()).
                quantity(inventory.getQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .availableQuantity(inventory.getAvailableQuantity())
                .build();
    }
}
