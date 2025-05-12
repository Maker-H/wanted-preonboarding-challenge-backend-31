package wanted.shop.product.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.product.domain.entity.*;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.TagId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductCreateRequest {

    private String name;

    private String slug;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("full_description")
    private String fullDescription;

    public ProductData toProductData() {
        return new ProductData(this.name, this.slug, this.shortDescription, this.fullDescription);
    }

    @JsonProperty("seller_id")
    private Long sellerId;

    public SellerId getSellerId() {
        return new SellerId(sellerId);
    }

    @JsonProperty("brand_id")
    private Long brandId;

    public BrandId getBrandId() {
        return new BrandId(brandId);
    }

    private String status;

    public ProductStatus toProductStatus() {
        return new ProductStatus(status);
    }

    @JsonProperty("categories")
    private List<CategoryRequest> categories;

    //TODO:
//    public List<CategoryId> getCategoryIds() {
//        return categories.stream().map(productCategory -> {
//            return new CategoryId(productCategory.getCategoryId());
//        }).toList();
//    }
//
//    public List<ProductCategory> toProductCategories() {
//        return categories.stream().map(category -> {
//            return ProductCategory.builder()
//                    .isPrimary(category.isPrimary())
//                    .build();
//        }).toList();
//    }

    @JsonProperty("tags")
    private List<Long> tagIds;

    public List<TagId> getTagIds() {
        return tagIds.stream().map(TagId::new).toList();
    }


    private Detail detail;

    public ProductDetail toProductDetail() {
        return ProductDetail.builder()
                .weight(detail.getWeight())
                .dimensions(detail.getDimensions().toString())
                .materials(detail.getMaterials())
                .countryOfOrigin(detail.getCountryOfOrigin())
                .warrantyInfo(detail.getWarrantyInfo())
                .careInstructions(detail.getCareInstructions())
                .additionalInfo(detail.getAdditionalInfo().toString())
                .build();
    }

    private Price price;

    public ProductPrice toProductPrice() {
        return ProductPrice.builder()
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .costPrice(price.getCostPrice())
                .currency(price.getCurrency())
                .taxRate(price.getTaxRate())
                .build();
    }

    private List<Image> images;

    public List<ProductImage> toProductImages() {
        return images.stream()
                .map(img -> ProductImage.builder()
                        .url(img.getUrl())
                        .altText(img.getAltText())
                        .isPrimary(img.isPrimary())
                        .displayOrder(img.getDisplayOrder())
                        .build())
                .toList();
    }


    @JsonProperty("option_groups")
    private List<OptionGroup> optionGroups;

    public List<ProductOptionGroup> toProductOptionGroup() {

        return optionGroups.stream().map(optionGroup -> {

            List<ProductOption> options = optionGroup.getOptions().stream().map(option -> {
                return ProductOption.builder()
                        .name(option.name)
                        .additionalPrice(option.additionalPrice)
                        .sku(option.sku)
                        .stock(option.stock)
                        .displayOrder(option.displayOrder)
                        .build();
            }).toList();


            ProductOptionGroup productOptionGroup = ProductOptionGroup.builder()
                    .displayOrder(optionGroup.displayOrder)
                    .name(optionGroup.name)
                    .build();

            System.out.println("ProductOptionGroup: " + options.size());
            productOptionGroup.addOption(options);

            return productOptionGroup;
        }).toList();
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Detail {
        private String weight;
        private Dimension dimensions;
        private String materials;

        @JsonProperty("country_of_origin")
        private String countryOfOrigin;

        @JsonProperty("warranty_info")
        private String warrantyInfo;

        @JsonProperty("care_instructions")
        private String careInstructions;

        @JsonProperty("additional_info")
        private Map<String, Object> additionalInfo;

        @Getter @Setter
        @NoArgsConstructor
        public static class Dimension {
            private int width;
            private int height;
            private int depth;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Price {
        @JsonProperty("base_price")
        private BigDecimal basePrice;

        @JsonProperty("sale_price")
        private BigDecimal salePrice;

        @JsonProperty("cost_price")
        private BigDecimal costPrice;

        private String currency;

        @JsonProperty("tax_rate")
        private BigDecimal taxRate;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class CategoryRequest {
        @JsonProperty("category_id")
        private Long categoryId;

        @JsonProperty("is_primary")
        private boolean isPrimary;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class OptionGroup {
        private String name;

        @JsonProperty("display_order")
        private int displayOrder;

        @JsonProperty("options")
        private List<Option> options;

        @Getter @Setter
        @NoArgsConstructor
        public static class Option {
            private String name;

            @JsonProperty("additional_price")
            private BigDecimal additionalPrice;

            private String sku;
            private int stock;

            @JsonProperty("display_order")
            private int displayOrder;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Image {
        private String url;

        @JsonProperty("alt_text")
        private String altText;

        @JsonProperty("is_primary")
        private boolean isPrimary;

        @JsonProperty("display_order")
        private int displayOrder;

        @JsonProperty("option_id")
        private Long optionId;
    }
}

