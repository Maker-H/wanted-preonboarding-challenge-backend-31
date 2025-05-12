package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "product_details")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_detail_id_seq")
    @SequenceGenerator(name = "product_detail_id_seq", sequenceName = "product_details_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        if (this.product == null) {
            this.product = product;
        }
    }

    private String weight;
    private String dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private String additionalInfo;
}
