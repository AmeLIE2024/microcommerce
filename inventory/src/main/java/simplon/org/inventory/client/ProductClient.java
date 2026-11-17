package simplon.org.inventory.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import simplon.org.inventory.dto.ProductDetails;

import java.util.UUID;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient.Builder builder, @Value("${product.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public ProductDetails getProductById(UUID productId) {
        return restClient.get()
                .uri("/products/{productId}", productId)
                .retrieve()
                .body(ProductDetails.class);
    }

}
