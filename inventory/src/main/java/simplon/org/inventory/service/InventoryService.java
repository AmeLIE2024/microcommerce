package simplon.org.inventory.service;

import org.springframework.stereotype.Service;
import simplon.org.inventory.client.ProductClient;
import simplon.org.inventory.dto.InventoryDetails;
import simplon.org.inventory.dto.ProductDetails;
import simplon.org.inventory.entity.Inventory;
import simplon.org.inventory.repository.InventoryRepository;

import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    public InventoryService(InventoryRepository inventoryRepository, ProductClient productClient) {
        this.inventoryRepository = inventoryRepository;
        this.productClient = productClient;
    }

    public Inventory createInventory(UUID productId, int initialQuantity) {
        Inventory inventory = new Inventory(null, productId, initialQuantity);
        return inventoryRepository.save(inventory);
    }

    public InventoryDetails getStockByProductId(UUID productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));
        ProductDetails product = productClient.getProductById(productId);
        return new InventoryDetails(product.name(), product.category(), inventory.getQuantity());
    }

    public void updateStock(UUID productId, int newQuantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for Product ID: " + productId));

        inventory.setQuantity(newQuantity);

        inventoryRepository.save(inventory);
    }
}
