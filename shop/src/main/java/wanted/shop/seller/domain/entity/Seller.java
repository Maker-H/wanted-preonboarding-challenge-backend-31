package wanted.shop.seller.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_id_seq")
    @SequenceGenerator(name = "seller_id_seq", sequenceName = "seller_id_seq", allocationSize = 1)
    private Long sellerId;

    public SellerId getSellerId() {
        return new SellerId(sellerId);
    }

    private String name;

    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    private BigDecimal rating;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}