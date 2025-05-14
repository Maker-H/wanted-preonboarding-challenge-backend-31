package wanted.shop.product.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.query.projection.ProductCategoryFlatProjection;

import java.util.List;

public interface ProductCategoryJpaRepository extends JpaRepository<Product, Long> {

    @Query(value = """
    SELECT
        pc.is_primary              AS category_is_primary,
        c.id                       AS category_id,
        c.name                     AS category_name,
        c.slug                     AS category_slug,
        parent.id                  AS parent_id,
        parent.name                AS parent_name,
        parent.slug                AS parent_slug
    FROM product_categories pc
    JOIN categories c ON pc.category_id = c.id
    LEFT JOIN categories parent ON c.parent_id = parent.id
    WHERE pc.product_id = :productId
    ORDER BY pc.id
    """, nativeQuery = true)
    List<ProductCategoryFlatProjection> findCategoryInfoByProductId(@Param("productId") Long productId);
}
