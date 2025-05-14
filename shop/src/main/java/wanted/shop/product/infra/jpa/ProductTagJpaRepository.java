package wanted.shop.product.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.query.projection.ProductTagProjection;

import java.util.List;

public interface ProductTagJpaRepository extends JpaRepository<Product, Long> {

    @Query(value = """
    SELECT
        t.id   AS tag_id,
        t.name AS tag_name,
        t.slug AS tag_slug
    FROM product_tags pt
    JOIN tags t ON pt.tag_id = t.id
    WHERE pt.product_id = :productId;
    """, nativeQuery = true)
    List<ProductTagProjection> findTagInfoByProductId(@Param("productId") Long productId);
}
