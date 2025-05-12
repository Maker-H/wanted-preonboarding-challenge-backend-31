package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_seq")
    @SequenceGenerator(name = "product_image_id_seq", sequenceName = "product_images_id_seq", allocationSize = 1)
    private Long id;

    public ImageId getId() {
        return new ImageId(this.id);
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        if (this.product == null) {
            this.product = product;
        }
    }

    @ManyToOne
    @JoinColumn(name = "option_id")
    private ProductOption option;

    private String url;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "display_order")
    private Integer displayOrder;

}

