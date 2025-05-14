package wanted.shop.review.command.dto;

import lombok.Setter;
import wanted.shop.review.domain.vo.ReviewData;

@Setter
public class ReviewCommand {
    private Integer rating;
    private String title;
    private String content;

    public ReviewData toReviewData() {
        return ReviewData.builder()
                .rating(rating)
                .title(title)
                .content(content)
                .build();
    }
}
