package wanted.shop.category.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private CategoryJpaRepository jpaRepository;

    public Optional<Category> findById(CategoryId categoryId) {
        return jpaRepository.findById(categoryId.getValue());
    }

    public Category save(Category category) {
        return jpaRepository.save(category);
    }

    public List<Category> findAllById(List<Long> categoryIds) {
        return jpaRepository.findAllById(categoryIds);
    }
}
