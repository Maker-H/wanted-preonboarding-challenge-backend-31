package wanted.shop.review.command.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.review.command.handler.DeleteReviewCommandHandler;
import wanted.shop.review.domain.vo.ReviewId;
import wanted.shop.review.command.dto.ReviewCommand;
import wanted.shop.review.command.dto.UpdateReviewResult;
import wanted.shop.review.command.handler.UpdateReviewCommandHandler;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewCommandController {

    private final DeleteReviewCommandHandler deleteReviewCommandHandler;
    private final UpdateReviewCommandHandler updateReviewCommandHandler;

    @DeleteMapping("/{reviewId}")
    public SuccessResponse<Void> deleteReview(@PathVariable Long reviewId){
        deleteReviewCommandHandler.handle(new ReviewId(reviewId));
        return new SuccessResponse<>(null, new Message("리뷰가 성공적으로 삭제되었습니다"));
    }

    @PutMapping("/{reviewId}")
    public SuccessResponse<UpdateReviewResult> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewCommand reviewCommand) {

        UpdateReviewResult response = updateReviewCommandHandler.handle(new ReviewId(reviewId), reviewCommand);
        return new SuccessResponse<>(response, new Message("리뷰가 성공적으로 수정되었습니다"));
    }
}
