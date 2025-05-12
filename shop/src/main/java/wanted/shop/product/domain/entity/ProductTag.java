package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.shop.tag.domain.entity.Tag;

@Builder
@Entity
@Table(name = "product_tags")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_tag_id_seq")
    @SequenceGenerator(name = "product_tag_id_seq", sequenceName = "product_tags_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        if (this.product == null) {
            this.product = product;
        }
    }

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
