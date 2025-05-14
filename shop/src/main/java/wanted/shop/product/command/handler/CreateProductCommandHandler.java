package wanted.shop.product.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.product.command.dto.CreateProductCommand;
import wanted.shop.product.command.dto.CreateProductResult;
import wanted.shop.product.command.mapper.CreateProductCommandMapper;
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
import java.util.Map;

@Service
@AllArgsConstructor
public class CreateProductCommandHandler {

    private final ProductCommandRepository productCommandRepository;
    private final CreateProductCommandMapper mapper;

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


        Map<CategoryId, Category> categoryMap = categoryLookupService.getCategoriesByIds(
                command.getCategories().stream().map(CreateProductCommand.ProductCategoryCommand::getCategoryId).toList()
        );

        Product product = Product.create(
                seller,
                brand,
                productTags,
                mapper.toProductStatus(command),
                mapper.toProductDetail(command.getDetail()),
                mapper.toProductPrice(command.getPrice()),
                mapper.toProductImages(command),
                mapper.toProductCategories(command, categoryMap),
                mapper.toProductData(command),
                mapper.toProductOptionGroups(command)
        );

        Product savedProduct = productCommandRepository.save(product);
        return CreateProductResult.from(savedProduct);
    }

}
