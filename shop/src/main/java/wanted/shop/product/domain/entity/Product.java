package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.product.dto.ProductCreateResponse;
import wanted.shop.seller.domain.entity.Seller;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private Long productId;

    public ProductId getProductId() {
        return new ProductId(this.productId);
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "shortDescription", column = @Column(name = "short_description")),
            @AttributeOverride(name = "fullDescription", column = @Column(name = "full_description"))
    })
    private ProductData productData;

    @Column(name = "status")
    private String productStatus;

    public ProductStatus getProductStatus() {
        return new ProductStatus(productStatus);
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdAt", column = @Column(name = "created_at")),
            @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at"))
    })
    private ProductTimestamps productTimestamps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDetail productDetail;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductPrice productPrice;

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTag> productTags = new ArrayList<>();

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
        productDetail.setProduct(this);
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
        productPrice.setProduct(this);
    }

    public void addProductTags(List<ProductTag> productTags) {
        this.productTags.addAll(productTags);
        productTags.forEach(tag -> tag.setProduct(this));
    }

    public void addProductImages(List<ProductImage> productImages) {
        this.productImages.addAll(productImages);
        productImages.forEach(image -> image.setProduct(this));
    }

    public void addProductOptions(List<ProductOptionGroup> productOptionGroups) {
        this.productOptionGroups.addAll(productOptionGroups);
        productOptionGroups.forEach(optionGroup -> optionGroup.setProduct(this));
    }

    public void addProductCategories(List<ProductCategory> groups) {
        this.productCategories.addAll(groups);
        productCategories.forEach(productCategory -> {
            productCategory.setProduct(this);
        });
    }

    public static Product create(
            Seller seller,
            Brand brand,
            List<ProductTag> productTags,
            ProductStatus productStatus,
            ProductDetail productDetail,
            ProductPrice productPrice,
            List<ProductImage> productImages,
            List<ProductCategory> productCategories,
            ProductData productData,
            List<ProductOptionGroup> productOptionGroups
    ) {

        Product product = Product.builder()
                .productData(productData)
                .productStatus(productStatus.getValue())
                .productTimestamps(ProductTimestamps.createNow())
                .seller(seller)
                .brand(brand)
                .build();

        product.setProductDetail(productDetail);
        product.setProductPrice(productPrice);

        product.addProductTags(productTags);
        product.addProductImages(productImages);
        product.addProductOptions(productOptionGroups);
        product.addProductCategories(productCategories);

        return product;
    }

    public ProductCreateResponse toCreateResponse() {
        return new ProductCreateResponse(
                this.productId,
                this.productData.getName(),
                this.productData.getSlug(),
                this.productTimestamps.getCreatedAt(),
                this.productTimestamps.getUpdatedAt()
        );
    }
}

