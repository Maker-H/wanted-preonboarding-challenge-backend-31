package wanted.shop.review.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.review.command.dto.ReviewCommand;
import wanted.shop.review.command.dto.UpdateReviewResult;
import wanted.shop.review.command.respository.ReviewCommandRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.vo.ReviewId;

@Service
@AllArgsConstructor
public class UpdateReviewCommandHandler {

    private ReviewCommandRepository reviewCommandRepository;

    @Transactional
    public UpdateReviewResult handle(ReviewId reviewId, ReviewCommand reviewCommand) {
        Review review = reviewCommandRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("없는 리뷰입니다"));

        review.updateReviewData(reviewCommand.toReviewData());
        return review.toUpdateResponse();
    }
}
