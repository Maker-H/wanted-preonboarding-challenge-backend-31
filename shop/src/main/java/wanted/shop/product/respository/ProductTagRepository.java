package wanted.shop.product.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.product.domain.entity.ProductId;
import wanted.shop.product.projection.ProductCategoryFlatProjection;
import wanted.shop.product.projection.ProductTagProjection;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductTagRepository {

    private ProductTagJpaRepository jpaRepository;

    public List<ProductTagProjection> findTagInfoByProductId(ProductId productId) {
        return jpaRepository.findTagInfoByProductId(productId.getValue());
    }
}
