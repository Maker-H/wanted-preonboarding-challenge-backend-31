package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.shop.product.domain.vo.ProductOptionGroupId;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "product_option_groups")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_option_group_id_seq")
    @SequenceGenerator(name = "product_option_group_id_seq", sequenceName = "product_option_groups_id_seq", allocationSize = 1)
    private Long productOptionId;

    public ProductOptionGroupId getProductOptionId() {
        return new ProductOptionGroupId(productOptionId);
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        if(this.product == null) {
            this.product = product;
        }
    }

    private String name;

    private Integer displayOrder;

    @Builder.Default
    @OneToMany(mappedBy = "productOptionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    public void addOption(List<ProductOption> productOptions) {
        this.productOptions.addAll(productOptions);
        productOptions.forEach(productOption -> productOption.setProductOptionGroup(this));
    }

    @Transient
    @Getter @Setter
    private List<ProductOptionGroupId> productOptionGroupIds;
}

