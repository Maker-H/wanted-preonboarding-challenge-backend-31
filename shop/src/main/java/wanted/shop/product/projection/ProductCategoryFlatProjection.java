package wanted.shop.product.projection;

import wanted.shop.product.dto.ProductResponse;

public interface ProductCategoryFlatProjection {
    Long getCategoryId();
    String getCategoryName();
    String getCategorySlug();
    Boolean getCategoryIsPrimary();
    Long getParentId();
    String getParentName();
    String getParentSlug();

    default ProductResponse.CategoryInfo toResponse() {
        ProductResponse.CategoryInfo.ParentCategory parentCategory = null;
        if (getCategoryId() != null) {
            ProductResponse.CategoryInfo.ParentCategory.builder()
                    .id(getParentId())
                    .name(getParentName())
                    .slug(getParentSlug())
                    .build();
        }

        return ProductResponse.CategoryInfo.builder()
                .id(getCategoryId())
                .name(getCategoryName())
                .slug(getCategorySlug())
                .isPrimary(getCategoryIsPrimary())
                .parent(parentCategory)
                .build();
    }

}
