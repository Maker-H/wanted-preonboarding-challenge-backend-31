package wanted.shop.review.query.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.infra.jpa.ReviewJpaRepository;

@Repository
@AllArgsConstructor
public class ReviewQueryRepository {

    private ReviewJpaRepository jpaRepository;

    public Page<Review> findAll(Specification<Review> spec, Pageable pageable) {
        return jpaRepository.findAll(spec, pageable);
    }

}
