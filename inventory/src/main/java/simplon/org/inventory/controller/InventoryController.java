package simplon.org.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simplon.org.inventory.dto.InventoryDetails;
import simplon.org.inventory.entity.Inventory;
import simplon.org.inventory.service.InventoryService;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{productId}")
    public Inventory initializeInventory(@PathVariable UUID productId, @RequestParam(defaultValue = "0") int initialQuantity) {
        Inventory inventory = inventoryService.createInventory(productId, initialQuantity);
        logger.info("Inventory initialized for Product ID: " + productId + " with initial quantity: " + initialQuantity);
        return inventory;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDetails> getStock(@PathVariable UUID productId) {
        return ResponseEntity.ok(inventoryService.getStockByProductId(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateStock(@PathVariable UUID productId, @RequestParam int newQuantity) {
        inventoryService.updateStock(productId, newQuantity);
        return ResponseEntity.ok("Stock updated for Product ID: " + productId + " with new quantity: " + newQuantity);
    }
}
