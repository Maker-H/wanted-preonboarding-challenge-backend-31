package wanted.shop.product.projection;

import wanted.shop.product.dto.ProductResponse;

public interface ProductTagProjection {
    Long getTagId();
    String getTagName();
    String getTagSlug();

    default ProductResponse.TagInfo toResponse() {
        return ProductResponse.TagInfo.builder()
                .id(getTagId())
                .name(getTagName())
                .slug(getTagSlug())
                .build();
    }
}

