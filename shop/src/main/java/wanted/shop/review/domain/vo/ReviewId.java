package wanted.shop.review.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class ReviewId implements Serializable {
    private final Long value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId reviewId)) return false;
        return Objects.equals(value, reviewId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
