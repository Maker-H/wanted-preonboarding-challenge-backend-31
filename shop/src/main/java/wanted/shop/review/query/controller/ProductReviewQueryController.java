package wanted.shop.review.query.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.review.query.dto.ReviewPagingQuery;
import wanted.shop.review.query.dto.GetReviewResult;
import wanted.shop.review.query.handler.GetReviewQueryHandler;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductReviewQueryController {

    private final GetReviewQueryHandler getReviewQueryHandler;

    @GetMapping("/{productId}/reviews")
    public SuccessResponse<GetReviewResult> getReviewsByProductId(
            @ModelAttribute @Valid ReviewPagingQuery request,
            @PathVariable Long productId
    ) {
        System.out.println(request.toString());
        GetReviewResult response = getReviewQueryHandler.handle(new ProductId(productId), request);
        return new SuccessResponse<>(response);
    }
}

