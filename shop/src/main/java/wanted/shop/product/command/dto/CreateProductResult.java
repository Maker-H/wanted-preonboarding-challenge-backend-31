package wanted.shop.product.command.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.shop.product.domain.entity.Product;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateProductResult {
    private final Long id;
    private final String name;
    private final String slug;

    @JsonProperty("created_at")
    private final String createdAt;

    @JsonProperty("updated_at")
    private final String updatedAt;

    public static CreateProductResult from(Product product) {
        return new CreateProductResult(
                product.getProductId().getValue(),
                product.getProductData().getName(),
                product.getProductData().getSlug(),
                product.getProductTimestamps().getCreatedAt().toString(),
                product.getProductTimestamps().getUpdatedAt().toString()
        );
    }
}
