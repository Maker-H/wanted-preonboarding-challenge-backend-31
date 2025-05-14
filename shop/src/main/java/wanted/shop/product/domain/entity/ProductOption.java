package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.shop.product.domain.vo.ProductOptionId;

import java.math.BigDecimal;

@Builder
@Entity
@Table(name = "product_options")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_option_id_seq")
    @SequenceGenerator(name = "product_option_id_seq", sequenceName = "product_options_id_seq", allocationSize = 1)
    private Long productOptionId;

    public ProductOptionId getProductOptionId() {
        return new ProductOptionId(productOptionId);
    }

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup productOptionGroup;

    public void setProductOptionGroup(ProductOptionGroup productOptionGroup) {
        if (this.productOptionGroup == null) {
            this.productOptionGroup = productOptionGroup;
        }
    }

    private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}

