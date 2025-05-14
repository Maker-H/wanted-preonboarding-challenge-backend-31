package wanted.shop.review.query.handler;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.shop.common.api.Pagination;
import wanted.shop.common.util.ObjectMapperUtil;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.query.dto.ReviewPagingQuery;
import wanted.shop.review.query.respository.spec.ReviewSpecification;
import wanted.shop.review.dto.*;
import wanted.shop.review.query.dto.GetReviewResult;
import wanted.shop.review.query.dto.ReviewRatingSummary;
import wanted.shop.review.query.respository.ReviewQueryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GetReviewQueryHandler {

    private ReviewQueryRepository reviewQueryRepository;

    @Transactional(readOnly = true)
    public GetReviewResult handle(ProductId productId, ReviewPagingQuery request) {

        Specification<Review> spec = request.getRating()
                .map(rating -> ReviewSpecification.withFilters(productId, rating))
                .orElse(ReviewSpecification.withFilters(productId));

        Page<Review> pagedReview = reviewQueryRepository.findAll(spec, request.toPageable());

        Pagination pagination = Pagination.from(pagedReview);

        List<ReviewDto> reviewDtoList = pagedReview.getContent().stream()
                .map(Review::toReviewDto)
                .toList();
        ReviewRatingSummary reviewSummaryDto = ReviewRatingSummary.from(pagedReview.getContent());

        GetReviewResult result = new GetReviewResult(reviewDtoList, reviewSummaryDto, pagination);
        ObjectMapperUtil.validateJsonSerializable(result);

        return result;
    }
}
