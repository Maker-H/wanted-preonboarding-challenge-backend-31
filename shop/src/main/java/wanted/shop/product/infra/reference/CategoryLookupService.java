package wanted.shop.product.infra.reference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.category.respository.CategoryRepository;

@Component
@AllArgsConstructor
public class CategoryLookupService {

    private final CategoryRepository categoryRepository;

    public Category getCategory(CategoryId categoryId) {
        return categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("없는 카테고리입니다"));
    }

}
