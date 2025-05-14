package wanted.shop.product.query.projection;

import wanted.shop.product.query.dto.ProductDto;

public interface ProductTagProjection {
    Long getTagId();
    String getTagName();
    String getTagSlug();

    default ProductDto.TagInfo toResponse() {
        return ProductDto.TagInfo.builder()
                .id(getTagId())
                .name(getTagName())
                .slug(getTagSlug())
                .build();
    }
}

