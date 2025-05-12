package wanted.shop.category.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private CategoryDataRepository dataRepository;

    public Optional<Category> findById(CategoryId categoryId) {
        return dataRepository.findById(categoryId.getValue());
    }

    public Category save(Category category) {
        return dataRepository.save(category);
    }
}
