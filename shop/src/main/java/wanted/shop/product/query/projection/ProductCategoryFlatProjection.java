package wanted.shop.product.query.projection;

import wanted.shop.product.query.dto.GetProductResult;

public interface ProductCategoryFlatProjection {
    Long getCategoryId();
    String getCategoryName();
    String getCategorySlug();
    Boolean getCategoryIsPrimary();
    Long getParentId();
    String getParentName();
    String getParentSlug();

    default GetProductResult.CategoryInfo toResponse() {
        GetProductResult.CategoryInfo.ParentCategory parentCategory = null;
        if (getCategoryId() != null) {
            GetProductResult.CategoryInfo.ParentCategory.builder()
                    .id(getParentId())
                    .name(getParentName())
                    .slug(getParentSlug())
                    .build();
        }

        return GetProductResult.CategoryInfo.builder()
                .id(getCategoryId())
                .name(getCategoryName())
                .slug(getCategorySlug())
                .isPrimary(getCategoryIsPrimary())
                .parent(parentCategory)
                .build();
    }

}
