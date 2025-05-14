package wanted.shop.product.infra.reference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.seller.respository.SellerRepository;

@Component
@AllArgsConstructor
public class SellerLookupService {

    private final SellerRepository sellerRepository;

    public Seller getSeller(SellerId sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("없는 seller입니다"));
    }

}
