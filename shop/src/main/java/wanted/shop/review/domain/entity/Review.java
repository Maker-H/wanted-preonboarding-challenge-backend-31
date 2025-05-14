package wanted.shop.review.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.shop.product.domain.entity.Product;
import wanted.shop.review.domain.vo.ReviewData;
import wanted.shop.review.domain.vo.ReviewId;
import wanted.shop.review.domain.vo.ReviewTimestamps;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.review.command.dto.UpdateReviewResult;
import wanted.shop.user.domain.User;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_seq")
    @SequenceGenerator(name = "review_id_seq", sequenceName = "reviews_id_seq", allocationSize = 1)
    private Long reviewId;

    public ReviewId getReviewId() {
        return new ReviewId(this.reviewId);
    }

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "rating", column = @Column(name = "rating")),
            @AttributeOverride(name = "title", column = @Column(name = "title")),
            @AttributeOverride(name = "content", column = @Column(name = "content"))
    })
    private ReviewData reviewData;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdAt", column = @Column(name = "created_at")),
            @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
            @AttributeOverride(name = "deletedAt", column = @Column(name = "deleted_at")),
    })
    private ReviewTimestamps timestamps;

    @Column(name = "verified_purchase")
    private boolean verifiedPurchase;

    @Column(name = "helpful_votes")
    private Integer helpfulVotes;

    public void delete() {
        this.timestamps.delete();
    }
    public ReviewDto toReviewDto() {
        return ReviewDto.builder()
                .id(reviewId)
                .user(ReviewDto.UserDto.builder()
                        .id(user.getId().getValue())
                        .name(user.getUserInfo().getName())
                        .email(user.getUserInfo().getEmail())
                        .avatarUrl(user.getUserInfo().getAvatarUrl())
                        .build())
                .rating(reviewData.getRating())
                .title(reviewData.getTitle())
                .content(reviewData.getContent())
                .createdAt(timestamps.getCreatedAt().toString())
                .updatedAt(timestamps.getUpdatedAt().toString())
                .verifiedPurchase(verifiedPurchase)
                .helpfulVotes(helpfulVotes)
                .build();
    }

    public static Review create(User user, Product product, ReviewData reviewData) {
        Review review = new Review();

        review.user = user;
        review.product = product;
        review.reviewData = reviewData;
        review.verifiedPurchase = true;
        review.helpfulVotes = 0;
        review.timestamps = ReviewTimestamps.createNow();

        System.out.println(reviewData);
        return review;
    }

    public UpdateReviewResult toUpdateResponse() {
        return UpdateReviewResult.builder()
                .id(this.reviewId)
                .rating(reviewData.getRating())
                .title(reviewData.getTitle())
                .content(reviewData.getContent())
                .updatedAt(timestamps.getUpdatedAt().toString())
                .build();
    }

    public void updateReviewData(ReviewData reviewData) {
        this.reviewData = reviewData;
        this.timestamps.markUpdated();
    }

}
