package wanted.shop.product.query.projection;

import wanted.shop.product.query.dto.GetProductResult;

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

    static List<GetProductResult.OptionGroupInfo> toResponse(List<ProductOptionFlatProjection> projections) {
        Map<Long, List<GetProductResult.OptionGroupInfo.OptionInfo>> optionGroupMap = projections.stream().collect(Collectors.groupingBy(
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

                    return GetProductResult.OptionGroupInfo.builder()
                            .id(optionGroup.getOptionGroupId())
                            .name(optionGroup.getOptionGroupName())
                            .displayOrder(optionGroup.getOptionGroupDisplayOrder())
                            .options(entry.getValue())
                            .build();
                }).toList();


    }

    private GetProductResult.OptionGroupInfo.OptionInfo toOptionInfo() {
        return GetProductResult.OptionGroupInfo.OptionInfo.builder()
                .id(getOptionId())
                .name(getOptionName())
                .additionalPrice(getOptionAdditionalPrice())
                .sku(getOptionSku())
                .stock(getOptionStock())
                .displayOrder(getOptionDisplayOrder())
                .build();
    }
}
