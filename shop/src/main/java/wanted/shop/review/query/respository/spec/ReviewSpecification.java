package wanted.shop.review.query.respository.spec;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import wanted.shop.product.domain.vo.ProductId;

import wanted.shop.product.domain.entity.Product_;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.Review_;
import wanted.shop.review.domain.vo.Rating;
import wanted.shop.review.domain.vo.ReviewData_;
import wanted.shop.review.domain.vo.ReviewTimestamps_;

import java.util.List;

public class ReviewSpecification {

    public static Specification<Review> withFilters(ProductId productId, Rating rating) {
        return (root, query, cb) -> {

            List<Predicate> predicates = List.of(
                    cb.equal(
                            root.get(Review_.product).get(Product_.productId),
                            productId.getValue()
                    ),
                    cb.equal(
                            root.get(Review_.reviewData).get(ReviewData_.RATING),
                            rating.getValue()
                    ),
                    cb.isNull(
                            root.get(Review_.timestamps).get(ReviewTimestamps_.DELETED_AT)
                    )
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Review> withFilters(ProductId productId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = List.of(
                    cb.equal(
                            root.get(Review_.product).get(Product_.productId),
                            productId.getValue()
                    ),
                    cb.isNull(
                            root.get(Review_.timestamps).get(ReviewTimestamps_.DELETED_AT)
                    ),
                    cb.isNull(
                            root.get(Review_.timestamps).get(ReviewTimestamps_.DELETED_AT)
                    )
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
