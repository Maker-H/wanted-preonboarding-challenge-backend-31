package wanted.shop.product.command.mapper;

import org.springframework.stereotype.Component;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.product.command.dto.UpdateProductCommand;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.domain.vo.*;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.tag.domain.entity.Tag;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateProductCommandMapper {

    public static ProductData toProductData(UpdateProductCommand command) {
        return new ProductData(command.getShortDescription(), command.getFullDescription(), command.getName(), command.getSlug());
    }

    public static ProductStatus toStatus(UpdateProductCommand command) {
        return new ProductStatus(command.getStatus());
    }

    public static ProductDetail toProductDetail(UpdateProductCommand.Detail detailDto) {
        return ProductDetail.builder()
                .weight(BigDecimal.valueOf(detailDto.getWeight()))
                .dimensions(new ProductDetailDimension(
                        detailDto.getDimensions().getWidth(),
                        detailDto.getDimensions().getHeight(),
                        detailDto.getDimensions().getDepth()
                ))
                .materials(detailDto.getMaterials())
                .countryOfOrigin(detailDto.getCountryOfOrigin())
                .warrantyInfo(detailDto.getWarrantyInfo())
                .careInstructions(detailDto.getCareInstructions())
                .additionalInfo(Map.of(
                        "assembly_required", detailDto.getAdditionalInfo().isAssemblyRequired(),
                        "assembly_time", detailDto.getAdditionalInfo().getAssemblyTime()
                ))
                .build();
    }

    public static ProductPrice toProductPrice(UpdateProductCommand.Price priceDto) {
        return ProductPrice.builder()
                .basePrice(priceDto.getBasePrice())
                .salePrice(priceDto.getSalePrice())
                .costPrice(priceDto.getCostPrice())
                .currency(priceDto.getCurrency())
                .taxRate(BigDecimal.valueOf(priceDto.getTaxRate()))
                .build();
    }

    public static List<Image> toImages(UpdateProductCommand command, Map<Long, ProductOption> optionMap) {
        return command.getImages().stream()
                .map(img -> Image.builder()
                        .url(img.getUrl())
                        .altText(img.getAltText())
                        .isPrimary(img.isPrimary())
                        .displayOrder(img.getDisplayOrder())
                        .option(img.getOptionId() != null ? optionMap.get(img.getOptionId()) : null)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<ProductCategory> toCategories(UpdateProductCommand command, Map<Long, Category> categoryMap) {
        return command.getCategories().stream()
                .map(cat -> ProductCategory.builder()
                        .category(categoryMap.get(cat.getCategoryId()))
                        .isPrimary(cat.isPrimary())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<ProductOptionGroup> toOptionGroups(UpdateProductCommand command) {
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

                    return ProductOptionGroup.builder()
                            .name(group.getName())
                            .displayOrder(group.getDisplayOrder())
                            .productOptions(options)
                            .build();
                }).collect(Collectors.toList());
    }

    public static List<ProductTag> toProductTags(List<Long> tagIds, Map<Long, Tag> tagMap) {
        return tagIds.stream()
                .map(id -> ProductTag.builder()
                        .tag(tagMap.get(id))
                        .build())
                .collect(Collectors.toList());
    }
}

