package wanted.shop.product.query.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.infra.jpa.ProductJpaRepository;
import wanted.shop.product.query.projection.ProductOptionFlatProjection;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductQueryRepository {

    private final ProductJpaRepository productJpaRepository;

    public Optional<Product> findById(ProductId productId) {
        return productJpaRepository.findById(productId.getValue());
    }

    public List<ProductOptionFlatProjection> findOptionInfosByProductId(ProductId productId) {
        return productJpaRepository.findOptionInfoByProductId(productId.getValue());
    }
}
