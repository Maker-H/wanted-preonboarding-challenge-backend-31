package wanted.shop.review.query.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import wanted.shop.common.dto.CommonPageRequest;
import wanted.shop.review.domain.vo.Rating;
import wanted.shop.review.query.respository.spec.ReviewSortField;

import java.util.Optional;

@Setter
public class ReviewPagingQuery extends CommonPageRequest {

    @Min(value = 1, message = "평점은 1 이상이어야 합니다")
    @Max(value = 5, message = "평점은 5 이하여야 합니다")
    private Integer rating;

    public ReviewPagingQuery() {
    }

    @Override
    public String toString() {
        return "ReviewPagingQuery{" +
                "rating=" + rating +
                ", page=" + page +
                ", perPage=" + perPage +
                ", sort='" + sort + '\'' +
                '}';
    }

    @Override
    public void setPage(int page) {
        this.page = Math.max(page - 1, 0);
    }

    @Override
    public Pageable toPageable() {
        String[] sortPart = sort.split(":");
        ReviewSortField reviewSortField = ReviewSortField.from(sortPart[0]);

        Sort.Direction direction = Sort.Direction.DESC;
        if (sortPart[1].equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        return PageRequest.of(page, perPage, Sort.by(direction, reviewSortField.getPath()));
    }

    public Optional<Rating> getRating() {
        return Optional.ofNullable(rating).map(Rating::new);
    }

}
