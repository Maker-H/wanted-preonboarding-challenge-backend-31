package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_seq")
    @SequenceGenerator(name = "product_image_id_seq", sequenceName = "product_images_id_seq", allocationSize = 1)
    private Long imageId;

    public ImageId getImageId() {
        return new ImageId(this.imageId);
    }

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public void setProduct(Product product) {
        if (this.product == null) {
            this.product = product;
        }
    }

    @JoinColumn(name = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption option;

    private String url;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "display_order")
    private Integer displayOrder;

}

