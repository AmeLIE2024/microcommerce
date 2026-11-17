package simplon.org.product.service;

import org.springframework.stereotype.Service;
import simplon.org.product.client.InventoryClient;
import simplon.org.product.entity.Product;
import simplon.org.product.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final InventoryClient inventoryClient;

    public ProductService(ProductRepository productRepository, InventoryClient inventoryClient) {
        this.productRepository = productRepository;
        this.inventoryClient = inventoryClient;
    }

  //création d'un produit et initialisation du stock à 0 dans le MS inventory
    public Product createProduct(Product product){
        Product savedProduct = productRepository.save(product);
        inventoryClient.initializeStock(savedProduct.getId());
        return savedProduct;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(UUID productId){
        return productRepository.findById(productId).orElse(null);
    }
}
