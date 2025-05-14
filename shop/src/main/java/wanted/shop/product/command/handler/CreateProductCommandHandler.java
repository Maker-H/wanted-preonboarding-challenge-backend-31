package wanted.shop.product.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.product.command.dto.CreateProductCommand;
import wanted.shop.product.command.dto.CreateProductResult;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.command.respository.ProductCommandRepository;
import wanted.shop.product.infra.reference.BrandLookupService;
import wanted.shop.product.infra.reference.CategoryLookupService;
import wanted.shop.product.infra.reference.SellerLookupService;
import wanted.shop.product.infra.reference.TagLookUpService;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.TagId;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateProductCommandHandler {

    private final ProductCommandRepository productCommandRepository;

    private final BrandLookupService brandLookupService;
    private final TagLookUpService tagLookUpService;
    private final SellerLookupService sellerLookupService;
    private final CategoryLookupService categoryLookupService;



    @Transactional
    public CreateProductResult handle(CreateProductCommand command) {

        SellerId sellerId = command.getSellerId();
        Seller seller = sellerLookupService.getSeller(sellerId);

        BrandId brandId = command.getBrandId();
        Brand brand = brandLookupService.getBrand(brandId);

        List<TagId> tagIds = command.getTagIds();
        List<ProductTag> productTags = tagLookUpService.getProductTags(tagIds);

        List<ProductCategory> productCategories = command.toProductCategories(categoryLookupService);

        Product product = Product.create(
                seller,
                brand,
                productTags,
                command.toProductStatus(),
                command.toProductDetail(),
                command.toProductPrice(),
                command.toProductImages(),
                productCategories,
                command.toProductData(),
                command.toProductOptionGroup()
        );

        Product savedProduct = productCommandRepository.save(product);

        return CreateProductResult.from(savedProduct);
    }

}
