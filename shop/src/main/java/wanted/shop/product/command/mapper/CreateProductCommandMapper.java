package wanted.shop.product.command.mapper;

import org.springframework.stereotype.Component;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.product.command.dto.CreateProductCommand;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.domain.vo.ProductData;
import wanted.shop.product.domain.vo.ProductDetailDimension;
import wanted.shop.product.domain.vo.ProductStatus;
import wanted.shop.tag.domain.entity.Tag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateProductCommandMapper {

    public ProductData toProductData(CreateProductCommand command) {
        return new ProductData(
                command.getName(),
                command.getSlug(),
                command.getShortDescription(),
                command.getFullDescription()
        );
    }

    public ProductStatus toProductStatus(CreateProductCommand command) {
        return new ProductStatus(command.getStatus());
    }

    public ProductDetail toProductDetail(CreateProductCommand.Detail detail) {
        return ProductDetail.builder()
                .weight(detail.getWeight())
                .dimensions(ProductDetailDimension.builder()
                        .width(detail.getDimension().getWidth())
                        .height(detail.getDimension().getHeight())
                        .depth(detail.getDimension().getDepth())
                        .build())
                .materials(detail.getMaterials())
                .countryOfOrigin(detail.getCountryOfOrigin())
                .warrantyInfo(detail.getWarrantyInfo())
                .careInstructions(detail.getCareInstructions())
                .additionalInfo(detail.getAdditionalInfo())
                .build();
    }

    public ProductPrice toProductPrice(CreateProductCommand.PriceCommand price) {
        return ProductPrice.builder()
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .costPrice(price.getCostPrice())
                .currency(price.getCurrency())
                .taxRate(price.getTaxRate())
                .build();
    }

    public List<ProductCategory> toProductCategories(CreateProductCommand command, Map<CategoryId, Category> categoryMap) {
        return command.getCategories().stream()
                .map(categoryCommand -> ProductCategory.builder()
                        .category(categoryMap.get(categoryCommand.getCategoryId()))
                        .isPrimary(categoryCommand.isPrimary())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProductTag> toProductTags(CreateProductCommand command, Map<Long, Tag> tagMap) {
        return command.getTagIds().stream()
                .map(id -> ProductTag.builder()
                        .tag(tagMap.get(id))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Image> toProductImages(CreateProductCommand command) {
        return command.getImages().stream()
                .map(img -> Image.builder()
                        .url(img.getUrl())
                        .altText(img.getAltText())
                        .isPrimary(img.isPrimary())
                        .displayOrder(img.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProductOptionGroup> toProductOptionGroups(CreateProductCommand command) {
        return command.getOptionGroups().stream()
                .map(group -> {
                    List<ProductOption> options = group.getOptions().stream()
                            .map(opt -> ProductOption.builder()
                                    .name(opt.getName())
                                    .additionalPrice(opt.getAdditionalPrice())
                                    .sku(opt.getSku())
                                    .stock(opt.getStock())
                                    .displayOrder(opt.getDisplayOrder())
                                    .build())
                            .collect(Collectors.toList());

                    ProductOptionGroup groupEntity = ProductOptionGroup.builder()
                            .name(group.getName())
                            .displayOrder(group.getDisplayOrder())
                            .build();

                    groupEntity.addOption(options);
                    return groupEntity;
                }).collect(Collectors.toList());
    }
}

