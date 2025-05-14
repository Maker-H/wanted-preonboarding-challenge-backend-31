package wanted.shop.product.command.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import wanted.shop.product.domain.entity.Product;

@Getter
@Builder
public class UpdateProductResult {

    private Long id;
    private String name;
    private String slug;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    public static UpdateProductResult from(Product product) {
        return UpdateProductResult.builder()
                .id(product.getProductId().getValue())
                .name(product.getProductData().getName())
                .slug(product.getProductData().getSlug())
                .createdAt(product.getProductTimestamps().getCreatedAt().toString())
                .updatedAt(product.getProductTimestamps().getUpdatedAt().toString())
                .build();
    }
}

