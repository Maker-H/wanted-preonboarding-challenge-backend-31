package wanted.shop.product.command.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.infra.jpa.ProductJpaRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductCommandRepository {

    private final ProductJpaRepository productJpaRepository;

    public Optional<Product> findById(ProductId productId) {
        return productJpaRepository.findById(productId.getValue());
    }

    public Product save(Product product) {
        return productJpaRepository.save(product);
    }



}
