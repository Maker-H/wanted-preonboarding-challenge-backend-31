package wanted.shop.review.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import wanted.shop.review.domain.entity.Review;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Builder
public class ReviewRatingSummary {

    @JsonProperty("average_rating")
    private final Double averageRating;

    @JsonProperty("total_count")
    private final Integer totalCount;

    @JsonProperty("distribution")
    private final Map<Integer, Long> ratingDistribution;

    public static ReviewRatingSummary from(List<Review> reviews) {

        Double rawAverage = reviews.stream()
                .mapToInt(review -> review.getReviewData().getRating())
                .average()
                .orElse(0.0);

        double roundedAverage = BigDecimal.valueOf(rawAverage)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        Map<Integer, Long> ratingDistribution = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> 0L,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<Integer, Long> counted = reviews.stream()
                .map(review -> review.getReviewData().getRating())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        ratingDistribution.putAll(counted);

        return ReviewRatingSummary.builder()
                .averageRating(roundedAverage)
                .totalCount(reviews.size())
                .ratingDistribution(ratingDistribution)
                .build();
    }



}