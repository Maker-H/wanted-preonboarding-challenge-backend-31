package wanted.shop.review.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.review.command.respository.ReviewCommandRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.vo.ReviewId;

@Service
@AllArgsConstructor
public class DeleteReviewCommandHandler {

    private ReviewCommandRepository reviewCommandRepository;

    @Transactional
    public void handle(ReviewId reviewId) {
        Review existingReview = reviewCommandRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("없는 리뷰입니다"));

        existingReview.delete();
        reviewCommandRepository.save(existingReview);
    }

}
