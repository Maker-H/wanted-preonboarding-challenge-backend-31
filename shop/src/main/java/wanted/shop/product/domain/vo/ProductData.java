package wanted.shop.product.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
}
