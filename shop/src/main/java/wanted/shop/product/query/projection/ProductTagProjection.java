package wanted.shop.product.query.projection;

import wanted.shop.product.query.dto.GetProductResult;

public interface ProductTagProjection {
    Long getTagId();
    String getTagName();
    String getTagSlug();

    default GetProductResult.TagInfo toResponse() {
        return GetProductResult.TagInfo.builder()
                .id(getTagId())
                .name(getTagName())
                .slug(getTagSlug())
                .build();
    }
}

