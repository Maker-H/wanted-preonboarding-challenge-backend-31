package wanted.shop.review.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.entity.ProductId;
import wanted.shop.product.respository.ProductRepository;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;
import wanted.shop.user.respository.UserRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ReviewReferenceService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public User findOrThrow(UserId userId) {
        return userRepository.findById(userId);
    }

    public Optional<Product> findOrThrow(ProductId productId) {
        return productRepository.findById(productId);
    }
}
