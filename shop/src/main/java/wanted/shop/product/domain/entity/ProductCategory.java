package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.shop.category.domain.entity.Category;

//TODO:
@Builder
@Entity
@Table(name = "product_categories")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_id_seq")
    @SequenceGenerator(name = "product_category_id_seq", sequenceName = "product_categories_id_seq", allocationSize = 1)
    private Long id;

    public ProductCategoryId getId() {
        return new ProductCategoryId(id);
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        if (product == null) {
            this.product = product;
        }
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void setCategory(Category category) {
        if (category == null) {
            this.category = category;
        }
    }

    private Boolean isPrimary;
}

