package wanted.shop.product.infra.reference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.product.domain.entity.ProductTag;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.domain.entity.TagId;
import wanted.shop.tag.respository.TagRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class TagLookUpService {

    private final TagRepository tagRepository;

    public List<ProductTag> getProductTags(List<TagId> tagIds) {
        return tagIds.stream()
                .map(tagId -> {
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new RuntimeException("없는 tag입니다"));

                    return ProductTag.builder().tag(tag).build();
                }).toList();
    }

}
