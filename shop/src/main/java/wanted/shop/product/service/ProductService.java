package wanted.shop.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.dto.ProductCreateRequest;
import wanted.shop.product.dto.ProductCreateResponse;
import wanted.shop.product.dto.ProductResponse;
import wanted.shop.product.projection.ProductCategoryFlatProjection;
import wanted.shop.product.projection.ProductOptionFlatProjection;
import wanted.shop.product.projection.ProductTagProjection;
import wanted.shop.product.respository.ProductCategoryRepository;
import wanted.shop.product.respository.ProductRepository;
import wanted.shop.product.respository.ProductTagRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.dto.ReviewRatingDto;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.TagId;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductReferenceService productReferenceService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request) {

        SellerId sellerId = request.getSellerId();
        Seller seller = productReferenceService.getSeller(sellerId);

        BrandId brandId = request.getBrandId();
        Brand brand = productReferenceService.getBrand(brandId);

        List<TagId> tagIds = request.getTagIds();
        List<ProductTag> productTags = productReferenceService.getProductTags(tagIds);

        List<ProductCategory> productCategories = request.toProductCategories();
        productCategories.forEach(productCategory -> {
            Category category = productReferenceService.getCategory(productCategory.getCategoryId());
            productCategory.setCategory(category);
        });

        Product product = Product.create(
                seller,
                brand,
                productTags,
                request.toProductStatus(),
                request.toProductDetail(),
                request.toProductPrice(),
                request.toProductImages(),
                productCategories,
                request.toProductData(),
                request.toProductOptionGroup()
        );

        Product savedProduct = productRepository.save(product);

        return ProductCreateResponse.from(savedProduct);
    }


    @Transactional(readOnly = true)
    public ProductResponse findProduct(ProductId productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("productId: " + productId + "를 조회할 수 없습니다."));

        ProductDetail productDetail = product.getProductDetail();
        ProductPrice productPrice = product.getProductPrice();
        List<Image> images = product.getImages();
        List<Review> reviews = product.getReviews();
        ReviewRatingDto reviewRatingDto = ReviewRatingDto.from(reviews);

        // 관계형 테이블 조회
        List<ProductResponse.CategoryInfo> categoryInfos = productCategoryRepository.findCategoryInfoByProductId(productId)
                .stream().map(ProductCategoryFlatProjection::toResponse).toList();

        List<ProductOptionFlatProjection> optionFlatProjections = productRepository.findOptionInfosByProductId(productId);
        List<ProductResponse.OptionGroupInfo> optionGroupInfos = ProductOptionFlatProjection.toResponse(optionFlatProjections);

        List<ProductResponse.TagInfo> tagInfos = productTagRepository.findTagInfoByProductId(productId)
                .stream().map(ProductTagProjection::toResponse).toList();

        Seller seller = productReferenceService.getSeller(product.getSeller().getSellerId());
        Brand brand = productReferenceService.getBrand(product.getBrand().getBrandId());

        return ProductResponse.from(
                product,
                seller,
                brand,
                productDetail,
                productPrice,
                images,
                categoryInfos,
                optionGroupInfos,
                tagInfos,
                reviewRatingDto
        );

    }
}
