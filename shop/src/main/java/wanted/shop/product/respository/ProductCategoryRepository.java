package wanted.shop.product.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.entity.ProductId;
import wanted.shop.product.projection.ProductCategoryFlatProjection;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductCategoryRepository {

    private ProductCategoryJpaRepository jpaRepository;

    public Optional<Product> findById(ProductId productId) {
        return jpaRepository.findById(productId.getValue());
    }

    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    public List<ProductCategoryFlatProjection> findCategoryInfoByProductId(ProductId productId) {
        return jpaRepository.findCategoryInfoByProductId(productId.getValue());
    }
}
