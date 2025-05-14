package wanted.shop.product.query.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.infra.reference.BrandLookupService;
import wanted.shop.product.infra.reference.SellerLookupService;
import wanted.shop.product.query.projection.ProductCategoryFlatProjection;
import wanted.shop.product.query.projection.ProductOptionFlatProjection;
import wanted.shop.product.query.projection.ProductTagProjection;
import wanted.shop.product.query.dto.ProductDto;
import wanted.shop.product.command.respository.ProductCategoryRepository;
import wanted.shop.product.query.respository.ProductQueryRepository;
import wanted.shop.product.query.respository.ProductTagQueryRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.query.dto.ReviewRatingSummary;
import wanted.shop.seller.domain.entity.Seller;

import java.util.List;

@Service
@AllArgsConstructor
public class GetProductDetailQueryHandler {

    private final ProductQueryRepository productQueryRepository;

    private final BrandLookupService brandLookupService;
    private final SellerLookupService sellerLookupService;

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductTagQueryRepository productTagRepository;

    @Transactional(readOnly = true)
    public ProductDto handle(ProductId productId) {

        Product product = productQueryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("productId: " + productId + "를 조회할 수 없습니다."));

        ProductDetail productDetail = product.getProductDetail();
        ProductPrice productPrice = product.getProductPrice();
        List<Image> images = product.getImages();
        List<Review> reviews = product.getReviews();
        ReviewRatingSummary reviewRatingSummary = ReviewRatingSummary.from(reviews);

        // 관계형 테이블 조회
        List<ProductDto.CategoryInfo> categoryInfos = productCategoryRepository.findCategoryInfoByProductId(productId)
                .stream().map(ProductCategoryFlatProjection::toResponse).toList();

        List<ProductOptionFlatProjection> optionFlatProjections = productQueryRepository.findOptionInfosByProductId(productId);
        List<ProductDto.OptionGroupInfo> optionGroupInfos = ProductOptionFlatProjection.toResponse(optionFlatProjections);

        List<ProductDto.TagInfo> tagInfos = productTagRepository.findTagInfoByProductId(productId)
                .stream().map(ProductTagProjection::toResponse).toList();

        Seller seller = sellerLookupService.getSeller(product.getSeller().getSellerId());
        Brand brand = brandLookupService.getBrand(product.getBrand().getBrandId());

        return ProductDto.from(
                product,
                seller,
                brand,
                productDetail,
                productPrice,
                images,
                categoryInfos,
                optionGroupInfos,
                tagInfos,
                reviewRatingSummary
        );

    }
}
