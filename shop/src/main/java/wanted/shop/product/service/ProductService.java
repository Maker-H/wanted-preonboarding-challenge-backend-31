package wanted.shop.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.entity.ProductCategory;
import wanted.shop.product.domain.entity.ProductTag;
import wanted.shop.product.dto.ProductCreateRequest;
import wanted.shop.product.dto.ProductCreateResponse;
import wanted.shop.product.respository.ProductRepository;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.domain.entity.TagId;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductReferenceService productReferenceService;
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




}
