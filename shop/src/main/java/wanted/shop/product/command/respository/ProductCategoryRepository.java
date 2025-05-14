package wanted.shop.product.command.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.infra.jpa.ProductCategoryJpaRepository;
import wanted.shop.product.query.projection.ProductCategoryFlatProjection;

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
