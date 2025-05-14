package wanted.shop.product.infra.reference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.brand.respository.BrandRepository;

@Component
@AllArgsConstructor
public class BrandLookupService {

    private final BrandRepository brandRepository;

    public Brand getBrand(BrandId brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("없는 brand입니다"));
    }

}
