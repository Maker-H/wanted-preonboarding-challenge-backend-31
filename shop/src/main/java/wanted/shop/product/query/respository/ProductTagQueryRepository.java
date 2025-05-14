package wanted.shop.product.query.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.infra.jpa.ProductTagJpaRepository;
import wanted.shop.product.query.projection.ProductTagProjection;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductTagQueryRepository {

    private ProductTagJpaRepository jpaRepository;

    public List<ProductTagProjection> findTagInfoByProductId(ProductId productId) {
        return jpaRepository.findTagInfoByProductId(productId.getValue());
    }
}
