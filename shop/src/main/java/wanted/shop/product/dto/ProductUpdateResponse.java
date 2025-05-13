package wanted.shop.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import wanted.shop.product.domain.entity.Product;

@Getter
@Builder
public class ProductUpdateResponse {

    private Long id;
    private String name;
    private String slug;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    public static ProductUpdateResponse from(Product product) {
        return ProductUpdateResponse.builder()
                .id(product.getProductId().getValue())
                .name(product.getProductData().getName())
                .slug(product.getProductData().getSlug())
                .createdAt(product.getProductTimestamps().getCreatedAt().toString())
                .updatedAt(product.getProductTimestamps().getUpdatedAt().toString())
                .build();
    }
}

