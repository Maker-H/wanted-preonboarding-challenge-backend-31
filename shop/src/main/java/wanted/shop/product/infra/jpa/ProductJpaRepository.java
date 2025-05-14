package wanted.shop.product.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.query.projection.ProductOptionFlatProjection;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    @Query(value = """
    SELECT
        og.id AS option_group_id,
        og.name AS option_group_name,
        og.display_order AS option_group_display_order,
        o.id AS option_id,
        o.name AS option_name,
        o.additional_price AS option_additional_price,
        o.sku AS option_sku,
        o.stock AS option_stock,
        o.display_order AS option_display_order
    FROM product_option_groups og
    JOIN product_options o ON og.id = o.option_group_id
    WHERE og.product_id = :productId
    ORDER BY og.display_order, o.display_order
    """, nativeQuery = true)
    List<ProductOptionFlatProjection> findOptionInfoByProductId(@Param("productId") Long productId);

}
