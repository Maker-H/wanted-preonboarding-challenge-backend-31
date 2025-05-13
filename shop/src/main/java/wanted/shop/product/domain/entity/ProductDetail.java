package wanted.shop.product.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Map;

@Getter
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

    private BigDecimal weight;

    @Type(JsonType.class)
    private ProductDetailDimension dimensions;

    @Column(name = "materials")
    private String materials;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "warranty_info")
    private String warrantyInfo;

    @Column(name = "care_instructions")
    private String careInstructions;

    @Type(JsonType.class)
    @Column(name = "additional_info")
    private Map<String, Object> additionalInfo;
}
