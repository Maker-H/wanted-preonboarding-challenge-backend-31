package wanted.shop.review.command.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateReviewResult {
    private Long id;
    private Integer rating;
    private String title;
    private String content;

    @JsonProperty("updated_at")
    private String updatedAt;
}
