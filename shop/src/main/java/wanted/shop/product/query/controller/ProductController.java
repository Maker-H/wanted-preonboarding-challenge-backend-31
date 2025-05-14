package wanted.shop.product.query.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.query.dto.GetProductResult;
import wanted.shop.product.query.handler.GetProductDetailQueryHandler;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final GetProductDetailQueryHandler getProductDetailQueryHandler;

    @GetMapping("/{productId}")
    public SuccessResponse<GetProductResult> findProduct(@PathVariable Long productId) {
        GetProductResult response = getProductDetailQueryHandler.handle(new ProductId(productId));
        return new SuccessResponse<>(response, new Message("상품 상세 정보를 성공적으로 조회했습니다."));
    }

}
