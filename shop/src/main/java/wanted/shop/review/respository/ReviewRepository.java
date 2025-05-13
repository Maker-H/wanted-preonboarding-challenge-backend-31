package wanted.shop.review.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReviewRepository {

    private ReviewJpaRepository jpaRepository;

    public Page<Review> findAll(Specification<Review> spec, Pageable pageable) {
        return jpaRepository.findAll(spec, pageable);
    }

    public Optional<Review> findById(ReviewId reviewId) {
        return jpaRepository.findById(reviewId.getValue());
    }

    public Review save(Review review) {
        return jpaRepository.save(review);
    }
}
