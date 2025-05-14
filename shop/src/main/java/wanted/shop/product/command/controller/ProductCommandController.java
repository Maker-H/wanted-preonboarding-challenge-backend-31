package wanted.shop.product.command.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.command.dto.CreateProductCommand;
import wanted.shop.product.command.dto.CreateProductResult;
import wanted.shop.product.command.handler.CreateProductCommandHandler;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductCommandController {

    private final CreateProductCommandHandler createProductCommandHandler;

    @PostMapping
    public SuccessResponse<CreateProductResult> createProduct(@RequestBody CreateProductCommand request) {
        CreateProductResult response = createProductCommandHandler.handle(request);
        return new SuccessResponse<>(response, new Message("상품이 성공적으로 등록되었습니다."));
    }

}
