package wanted.shop.review.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import wanted.shop.common.api.Pagination;
import wanted.shop.review.dto.ReviewDto;

import java.util.List;

@Getter
@JsonPropertyOrder({ "items", "summary", "pagination" })
public class GetReviewResult {

    @JsonProperty("items")
    private final List<ReviewDto> items;

    @JsonProperty("summary")
    private final ReviewRatingSummary summary;

    @JsonProperty("pagination")
    private final Pagination pagination;

    public GetReviewResult(List<ReviewDto> items, ReviewRatingSummary summary, Pagination pagination) {
        this.items = items;
        this.summary = summary;
        this.pagination = pagination;
    }
}