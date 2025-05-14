package wanted.shop.product.infra.reference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.domain.entity.CategoryId;
import wanted.shop.category.respository.CategoryRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryLookupService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryById(CategoryId categoryId) {
        return categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("없는 카테고리입니다"));
    }


    public Map<CategoryId, Category> getCategoriesByIds(List<CategoryId> categoryIds) {
        List<Long> ids = categoryIds.stream()
                .map(CategoryId::getValue)
                .toList();

        List<Category> categories = categoryRepository.findAllById(ids);

        return categories.stream()
                .collect(Collectors.toMap(Category::getCategoryId, category -> category));
    }
}
