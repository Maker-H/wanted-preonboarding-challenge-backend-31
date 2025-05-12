package wanted.shop.category.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.product.domain.entity.Product;

public interface CategoryDataRepository extends JpaRepository<Category, Long> {
}
