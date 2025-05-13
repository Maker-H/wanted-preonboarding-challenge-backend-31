package wanted.shop.category.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.category.domain.entity.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
