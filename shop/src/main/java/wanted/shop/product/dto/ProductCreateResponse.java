package wanted.shop.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.shop.product.domain.entity.Product;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductCreateResponse {
    private final Long id;
    private final String name;
    private final String slug;

    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private final LocalDateTime updatedAt;

    public static ProductCreateResponse from(Product product) {
        return new ProductCreateResponse(
                product.getProductId().getValue(),
                product.getProductData().getName(),
                product.getProductData().getSlug(),
                product.getProductTimestamps().getCreatedAt(),
                product.getProductTimestamps().getUpdatedAt()
        );
    }
}
