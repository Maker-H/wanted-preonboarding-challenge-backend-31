package wanted.shop.product.command.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.product.domain.vo.ProductData;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.TagId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class CreateProductCommand {

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

    @JsonProperty("categories")
    private List<ProductCategoryCommand> categories;

    @JsonProperty("tags")
    private List<Long> tagIds;

    public List<TagId> getTagIds() {
        return tagIds.stream().map(TagId::new).toList();
    }

    private Detail detail;

    private PriceCommand price;

    private List<ImageCommand> images;

    @JsonProperty("option_groups")
    private List<OptionGroupCommand> optionGroups;

    @Getter @Setter
    @NoArgsConstructor
    public static class Detail {
        private BigDecimal weight;

        @JsonProperty("dimensions")
        private DimensionCommand dimension;

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
        public static class DimensionCommand {
            private int width;
            private int height;
            private int depth;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class PriceCommand {
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
    public static class ProductCategoryCommand {
        @JsonProperty("category_id")
        private Long categoryId;

        public CategoryId getCategoryId() {
            return new CategoryId(categoryId);
        }

        @JsonProperty("is_primary")
        private boolean isPrimary;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class OptionGroupCommand {
        private String name;

        @JsonProperty("display_order")
        private int displayOrder;

        @JsonProperty("options")
        private List<OptionCommand> options;

        @Getter @Setter
        @NoArgsConstructor
        public static class OptionCommand {
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
    public static class ImageCommand {
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

