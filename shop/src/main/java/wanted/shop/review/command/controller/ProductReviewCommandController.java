package wanted.shop.review.command.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.review.command.handler.CreateReviewCommandHandler;
import wanted.shop.review.command.dto.ReviewCommand;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.user.domain.UserId;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductReviewCommandController {

    private final CreateReviewCommandHandler createReviewCommandHandler;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<SuccessResponse<Object>> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewCommand reviewCommand) {

        // 임의의 유저
        ReviewDto response = createReviewCommandHandler.handle(reviewCommand, new UserId(1L), new ProductId(productId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(response, new Message("리뷰가 성공적으로 등록되었습니다.")));
    }
}

