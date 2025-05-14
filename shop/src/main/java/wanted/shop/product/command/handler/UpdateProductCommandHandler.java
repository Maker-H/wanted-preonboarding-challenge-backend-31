package wanted.shop.product.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.respository.BrandRepository;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.category.respository.CategoryRepository;
import wanted.shop.product.command.dto.UpdateProductCommand;
import wanted.shop.product.command.dto.UpdateProductResult;
import wanted.shop.product.command.mapper.UpdateProductCommandMapper;
import wanted.shop.product.command.respository.ProductCommandRepository;
import wanted.shop.product.domain.entity.*;
import wanted.shop.product.domain.vo.ProductData;
import wanted.shop.product.domain.vo.ProductId;
import wanted.shop.product.domain.vo.ProductStatus;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.respository.SellerRepository;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.respository.TagRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpdateProductCommandHandler {

    private final ProductCommandRepository productRepository;
    private final BrandRepository brandRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
//
//    public UpdateProductResult handle(ProductId productId, UpdateProductCommand command) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
//
//        // 연관 엔티티 조회
//        Seller seller = sellerRepository.findById(command.getSellerId()).orElseThrow();
//        Brand brand = brandRepository.findById(command.getBrandId()).orElseThrow();
//        Map<Long, Category> categoryMap = categoryRepository.findAllByIdIn(
//                        command.getCategories().stream().map(UpdateProductCommand.Category::getCategoryId).toList())
//                .stream().collect(Collectors.toMap(Category::getId, c -> c));
//
//        Map<Long, Tag> tagMap = tagRepository.findAllByIdIn(command.getTags())
//                .stream().collect(Collectors.toMap(Tag::getId, t -> t));
//
//        // 변환
//        ProductData data = UpdateProductCommandMapper.toProductData(command);
//        ProductStatus status = UpdateProductCommandMapper.toStatus(command);
//        ProductDetail detail = UpdateProductCommandMapper.toProductDetail(command.getDetail());
//        ProductPrice price = UpdateProductCommandMapper.toProductPrice(command.getPrice());
//        List<ProductOptionGroup> optionGroups = UpdateProductCommandMapper.toOptionGroups(command);
//        List<ProductCategory> categories = UpdateProductCommandMapper.toCategories(command, categoryMap);
//        List<ProductTag> productTags = UpdateProductCommandMapper.toProductTags(command.getTags(), tagMap);
//        List<Image> images = UpdateProductCommandMapper.toImages(command, Map.of());
//
//        // 도메인 update
//        product.update(data, status, brand, seller, detail, price, images, categories, productTags, optionGroups);
//    }
}
