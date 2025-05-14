package wanted.shop.product.query.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.domain.vo.ProductDetailDimension;
import wanted.shop.review.query.dto.ReviewRatingSummary;
import wanted.shop.seller.domain.entity.Seller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String slug;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("full_description")
    private String fullDescription;

    private SellerInfo seller;
    private BrandInfo brand;
    private String status;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    private ProductDetailInfo detail;
    private ProductPriceInfo price;
    private List<CategoryInfo> categories;

    @JsonProperty("option_groups")
    private List<OptionGroupInfo> optionGroups;

    private List<ImageInfo> images;
    private List<TagInfo> tags;

    @JsonProperty("rating")
    private ReviewRatingSummary reviewRatingSummary;

    @JsonProperty("related_products")
    private List<RelatedProductInfo> relatedProducts;

    //TODO:연관 상품 조회
    public static ProductDto from(
            Product product,
            Seller seller,
            Brand brand,
            ProductDetail productDetail,
            ProductPrice productPrice,
            List<Image> images,
            List<CategoryInfo> categoryInfos,
            List<OptionGroupInfo> optionGroupInfos,
            List<TagInfo> tagInfos,
            ReviewRatingSummary reviewRatingSummary
    ) {
        return ProductDto.builder()
                .id(product.getProductId().getValue())
                .name(product.getProductData().getName())
                .slug(product.getProductData().getSlug())
                .shortDescription(product.getProductData().getShortDescription())
                .fullDescription(product.getProductData().getFullDescription())
                .seller(SellerInfo.from(seller))
                .brand(BrandInfo.from(brand))
                .status(product.getProductStatus().getValue())
                .createdAt(product.getProductTimestamps().getCreatedAt().toString())
                .updatedAt(product.getProductTimestamps().getUpdatedAt().toString())
                .detail(ProductDetailInfo.from(productDetail))
                .price(ProductPriceInfo.from(productPrice))
                .categories(categoryInfos)
                .optionGroups(optionGroupInfos)
                .images(
                        images.stream().map(ImageInfo::from).toList()
                )
                .tags(tagInfos)
                .reviewRatingSummary(reviewRatingSummary)
                .relatedProducts(List.of()) // 관련 상품은 별도 서비스 필요
                .build();
    }

    @Getter @Builder
    public static class SellerInfo {
        private Long id;
        private String name;
        private String description;
        @JsonProperty("logo_url") private String logoUrl;
        @JsonProperty("rating") private BigDecimal rating;
        @JsonProperty("contact_email") private String contactEmail;
        @JsonProperty("contact_phone") private String contactPhone;

        public static SellerInfo from(Seller seller) {
            return SellerInfo.builder()
                    .id(seller.getSellerId().getValue())
                    .name(seller.getName())
                    .description(seller.getDescription())
                    .logoUrl(seller.getLogoUrl())
                    .rating(seller.getRating())
                    .contactEmail(seller.getContactEmail())
                    .contactPhone(seller.getContactPhone())
                    .build();
        }
    }

    @Getter @Builder
    public static class BrandInfo {
        private Long id;
        private String name;
        private String description;
        @JsonProperty("logo_url") private String logoUrl;
        private String website;

        public static BrandInfo from(Brand brand) {
            return BrandInfo.builder()
                    .id(brand.getBrandId().getValue())
                    .name(brand.getName())
                    .description(brand.getDescription())
                    .logoUrl(brand.getLogoUrl())
                    .website(brand.getWebsite())
                    .build();
        }
    }

    @Getter @Builder
    public static class ProductDetailInfo {
        private BigDecimal weight;
        private Dimensions dimensions;
        private String materials;
        @JsonProperty("country_of_origin") private String countryOfOrigin;
        @JsonProperty("warranty_info") private String warrantyInfo;
        @JsonProperty("care_instructions") private String careInstructions;
        @JsonProperty("additional_info") private Map<String, Object> additionalInfo;

        public static ProductDetailInfo from(ProductDetail detail) {
            return ProductDetailInfo.builder()
                    .weight(detail.getWeight())
                    .dimensions(Dimensions.from(detail.getDimensions()))
                    .materials(detail.getMaterials())
                    .countryOfOrigin(detail.getCountryOfOrigin())
                    .warrantyInfo(detail.getWarrantyInfo())
                    .careInstructions(detail.getCareInstructions())
                    .additionalInfo(detail.getAdditionalInfo())
                    .build();
        }

        @Getter @Builder
        public static class Dimensions {
            private int width;
            private int height;
            private int depth;

            public static Dimensions from(ProductDetailDimension detailDimension) {
                return Dimensions.builder()
                        .width(detailDimension.getWidth())
                        .height(detailDimension.getHeight())
                        .depth(detailDimension.getDepth())
                        .build();
            }
        }
    }

    @Getter @Builder
    public static class ProductPriceInfo {
        @JsonProperty("base_price") private BigDecimal basePrice;
        @JsonProperty("sale_price") private BigDecimal salePrice;
        private String currency;
        @JsonProperty("tax_rate") private BigDecimal taxRate;
        @JsonProperty("discount_percentage") private double discountPercentage;

        public static ProductPriceInfo from(ProductPrice price) {

            BigDecimal base = price.getBasePrice();
            BigDecimal sale = price.getSalePrice();

            double discount = 0;
            if (base.compareTo(BigDecimal.ZERO) > 0 && base.compareTo(sale) > 0) {
                discount = Math.round(
                        (base.subtract(sale))
                                .divide(base, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100))
                                .doubleValue()
                );
            }

            return ProductPriceInfo.builder()
                    .basePrice(base)
                    .salePrice(sale)
                    .currency(price.getCurrency())
                    .taxRate(price.getTaxRate())
                    .discountPercentage(discount)
                    .build();
        }
    }

    @Getter @Builder
    public static class CategoryInfo {
        private long id;
        private String name;
        private String slug;

        @JsonProperty("is_primary") private boolean isPrimary;
        private ParentCategory parent;

        @Getter @Builder
        public static class ParentCategory {
            private Long id;
            private String name;
            private String slug;
        }

    }

    @Getter @Builder
    public static class OptionGroupInfo {
        private Long id;
        private String name;
        @JsonProperty("display_order") private int displayOrder;
        private List<OptionInfo> options;

        @Getter @Builder
        public static class OptionInfo {
            private Long id;
            private String name;
            @JsonProperty("additional_price") private BigDecimal additionalPrice;
            private String sku;
            private int stock;
            @JsonProperty("display_order") private int displayOrder;

        }
    }

    @Getter @Builder
    public static class ImageInfo {
        private Long id;
        private String url;
        @JsonProperty("alt_text") private String altText;
        @JsonProperty("is_primary") private boolean isPrimary;
        @JsonProperty("display_order") private int displayOrder;
        @JsonProperty("option_id") private Long optionId;

        public static ImageInfo from(Image image) {
            return ImageInfo.builder()
                    .id(image.getImageId().getValue())
                    .url(image.getUrl())
                    .altText(image.getAltText())
                    .isPrimary(image.getIsPrimary())
                    .displayOrder(image.getDisplayOrder())
                    .optionId(null)
                    .build();
        }
    }

    @Getter @Builder
    public static class TagInfo {
        private Long id;
        private String name;
        private String slug;
    }

    @Getter @Builder
    public static class RelatedProductInfo {
        private Long id;
        private String name;
        private String slug;
        @JsonProperty("short_description") private String shortDescription;
        @JsonProperty("primary_image") private ImageData primaryImage;
        @JsonProperty("base_price") private BigDecimal basePrice;
        @JsonProperty("sale_price") private BigDecimal salePrice;
        private String currency;

        @Getter @Builder
        public static class ImageData {
            private String url;
            @JsonProperty("alt_text") private String altText;
        }
    }
}
