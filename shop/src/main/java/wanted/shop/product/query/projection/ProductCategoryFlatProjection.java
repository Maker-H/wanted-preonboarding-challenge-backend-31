package wanted.shop.product.query.projection;

import wanted.shop.product.query.dto.ProductDto;

public interface ProductCategoryFlatProjection {
    Long getCategoryId();
    String getCategoryName();
    String getCategorySlug();
    Boolean getCategoryIsPrimary();
    Long getParentId();
    String getParentName();
    String getParentSlug();

    default ProductDto.CategoryInfo toResponse() {
        ProductDto.CategoryInfo.ParentCategory parentCategory = null;
        if (getCategoryId() != null) {
            ProductDto.CategoryInfo.ParentCategory.builder()
                    .id(getParentId())
                    .name(getParentName())
                    .slug(getParentSlug())
                    .build();
        }

        return ProductDto.CategoryInfo.builder()
                .id(getCategoryId())
                .name(getCategoryName())
                .slug(getCategorySlug())
                .isPrimary(getCategoryIsPrimary())
                .parent(parentCategory)
                .build();
    }

}
