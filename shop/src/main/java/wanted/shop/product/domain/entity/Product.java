package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.product.domain.vo.ProductData;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.domain.vo.ProductStatus;
import wanted.shop.product.domain.vo.ProductTimestamps;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.seller.domain.entity.Seller;

import java.util.ArrayList;
import java.util.List;

@Getter
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
    private List<Image> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductTag> productTags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

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

    public void addProductImages(List<Image> images) {
        this.images.addAll(images);
        images.forEach(image -> image.setProduct(this));
    }

    public void addProductOptions(List<ProductOptionGroup> productOptionGroups) {
        this.productOptionGroups.addAll(productOptionGroups);
        productOptionGroups.forEach(optionGroup -> optionGroup.setProduct(this));
    }

    public void addProductCategories(List<ProductCategory> productCategories) {
        this.productCategories.addAll(productCategories);
        this.productCategories.forEach(productCategory -> {
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
            List<Image> images,
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
        product.addProductImages(images);
        product.addProductOptions(productOptionGroups);
        product.addProductCategories(productCategories);

        return product;
    }

    public void update(
            ProductData productData,
            ProductStatus productStatus,
            Brand brand,
            Seller seller,
            ProductDetail productDetail,
            ProductPrice productPrice,
            List<Image> newImages,
            List<ProductCategory> newCategories,
            List<ProductTag> newTags,
            List<ProductOptionGroup> newOptionGroups
    ) {
        // 값 객체
        this.productData = productData;
        this.productStatus = productStatus.getValue();
        this.brand = brand;
        this.seller = seller;

        // 연관 객체는 내부 setter 호출로 관계 연결
        setProductDetail(productDetail);
        setProductPrice(productPrice);

        // 기존 연관 컬렉션 교체
        this.images.clear();
        addProductImages(newImages);

        this.productOptionGroups.clear();
        addProductOptions(newOptionGroups);

        this.productCategories.clear();
        addProductCategories(newCategories);

        this.productTags.clear();
        addProductTags(newTags);

        this.productTimestamps.markUpdated();
    }


}

