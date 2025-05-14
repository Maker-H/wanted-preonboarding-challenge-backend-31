package wanted.shop.review.command.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.vo.ReviewId;
import wanted.shop.review.infra.jpa.ReviewJpaRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReviewCommandRepository {

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
