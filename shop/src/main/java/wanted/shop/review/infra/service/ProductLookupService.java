package wanted.shop.review.infra.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.query.respository.ProductQueryRepository;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;
import wanted.shop.user.respository.UserRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductLookupService {

    private final ProductQueryRepository productQueryRepository;

    public Optional<Product> findOrThrow(ProductId productId) {
        return productQueryRepository.findById(productId);
    }
}
