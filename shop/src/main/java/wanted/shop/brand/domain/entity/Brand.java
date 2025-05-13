package wanted.shop.brand.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "brands")
@Access(AccessType.FIELD)
public class Brand {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_seq")
    @SequenceGenerator(name = "brand_id_seq", sequenceName = "brand_id_seq", allocationSize = 1)
    private Long brandId;

    public BrandId getBrandId() {
        return new BrandId(brandId);
    }

    private String name;

    private String slug;

    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    private String website;

}
