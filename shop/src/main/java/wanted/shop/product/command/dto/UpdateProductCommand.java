package wanted.shop.product.command.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class UpdateProductCommand {

    private String name;
    private String slug;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("full_description")
    private String fullDescription;

    @JsonProperty("seller_id")
    private Long sellerId;

    @JsonProperty("brand_id")
    private Long brandId;

    private String status;

    private Detail detail;
    private Price price;

    private List<Category> categories;

    @JsonProperty("option_groups")
    private List<OptionGroup> optionGroups;

    private List<Image> images;

    private List<Long> tags;

    @Getter
    public static class Detail {
        private double weight;
        private Dimensions dimensions;

        private String materials;

        @JsonProperty("country_of_origin")
        private String countryOfOrigin;

        @JsonProperty("warranty_info")
        private String warrantyInfo;

        @JsonProperty("care_instructions")
        private String careInstructions;

        @JsonProperty("additional_info")
        private AdditionalInfo additionalInfo;
    }

    @Getter
    public static class Dimensions {
        private int width;
        private int height;
        private int depth;
    }

    @Getter
    public static class AdditionalInfo {
        @JsonProperty("assembly_required")
        private boolean assemblyRequired;

        @JsonProperty("assembly_time")
        private String assemblyTime;
    }

    @Getter
    public static class Price {
        @JsonProperty("base_price")
        private BigDecimal basePrice;

        @JsonProperty("sale_price")
        private BigDecimal salePrice;

        @JsonProperty("cost_price")
        private BigDecimal costPrice;

        private String currency;

        @JsonProperty("tax_rate")
        private int taxRate;
    }

    @Getter
    public static class Category {
        @JsonProperty("category_id")
        private Long categoryId;

        @JsonProperty("is_primary")
        private boolean isPrimary;
    }

    @Getter
    public static class OptionGroup {
        private String name;

        @JsonProperty("display_order")
        private int displayOrder;

        private List<Option> options;
    }

    @Getter
    public static class Option {
        private String name;

        @JsonProperty("additional_price")
        private BigDecimal additionalPrice;

        private String sku;
        private int stock;

        @JsonProperty("display_order")
        private int displayOrder;
    }

    @Getter
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
