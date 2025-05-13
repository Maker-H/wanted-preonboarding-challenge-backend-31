package wanted.shop.category.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 1)
    private Long categoryId;

    public CategoryId getCategoryId() {
        return new CategoryId(categoryId);
    }

    @Getter
    private String name;

    @Getter
    private String slug;

    @Getter
    private String description;

    @Getter
    private String imageUrl;

    @Getter
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    public CategoryId getParentCategoryId() {
        if (parent == null) {
            return null;
        }

        return parent.getCategoryId();
    }


}