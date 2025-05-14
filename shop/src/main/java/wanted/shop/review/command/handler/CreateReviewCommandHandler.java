package wanted.shop.review.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.review.command.respository.ReviewCommandRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.command.dto.ReviewCommand;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.review.infra.service.ProductLookupService;
import wanted.shop.review.infra.service.UserLookupService;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;

@Service
@AllArgsConstructor
public class CreateReviewCommandHandler {

    private ReviewCommandRepository reviewCommandRepository;
    private final ProductLookupService productLookupService;
    private final UserLookupService userLookupService;


    @Transactional
    public ReviewDto handle(ReviewCommand reviewCommand, UserId userId, ProductId productId) {
        User user = userLookupService.findOrThrow(userId);
        Product product = productLookupService.findOrThrow(productId)
                .orElseThrow(() -> new RuntimeException("productId: " + productId.getValue() + "를 조회할 수 없습니다"));

        Review createdReview = Review.create(user, product, reviewCommand.toReviewData());
        Review savedReview = reviewCommandRepository.save(createdReview);

        return savedReview.toReviewDto();

    }
}
