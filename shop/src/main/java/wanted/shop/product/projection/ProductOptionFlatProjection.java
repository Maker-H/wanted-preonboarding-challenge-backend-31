package wanted.shop.product.projection;

import wanted.shop.product.dto.ProductResponse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public interface ProductOptionFlatProjection {
    Long getOptionGroupId();
    String getOptionGroupName();
    Integer getOptionGroupDisplayOrder();

    Long getOptionId();
    String getOptionName();
    BigDecimal getOptionAdditionalPrice();
    String getOptionSku();
    Integer getOptionStock();
    Integer getOptionDisplayOrder();

    static List<ProductResponse.OptionGroupInfo> toResponse(List<ProductOptionFlatProjection> projections) {
        Map<Long, List<ProductResponse.OptionGroupInfo.OptionInfo>> optionGroupMap = projections.stream().collect(Collectors.groupingBy(
                ProductOptionFlatProjection::getOptionGroupId,
                Collectors.mapping(
                        ProductOptionFlatProjection::toOptionInfo,
                        Collectors.toList()
                )
        ));

        return optionGroupMap.entrySet().stream()
                .map(entry -> {
                    Long groupId = entry.getKey();

                    ProductOptionFlatProjection optionGroup = projections.stream()
                            .filter(p -> p.getOptionGroupId().equals(groupId))
                            .findFirst()
                            .orElseThrow();

                    return ProductResponse.OptionGroupInfo.builder()
                            .id(optionGroup.getOptionGroupId())
                            .name(optionGroup.getOptionGroupName())
                            .displayOrder(optionGroup.getOptionGroupDisplayOrder())
                            .options(entry.getValue())
                            .build();
                }).toList();


    }

    private ProductResponse.OptionGroupInfo.OptionInfo toOptionInfo() {
        return ProductResponse.OptionGroupInfo.OptionInfo.builder()
                .id(getOptionId())
                .name(getOptionName())
                .additionalPrice(getOptionAdditionalPrice())
                .sku(getOptionSku())
                .stock(getOptionStock())
                .displayOrder(getOptionDisplayOrder())
                .build();
    }
}
