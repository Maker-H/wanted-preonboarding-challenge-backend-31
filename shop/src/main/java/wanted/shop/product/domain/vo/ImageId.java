package wanted.shop.product.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class ImageId implements Serializable {
    private final Long value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageId productId)) return false;
        return Objects.equals(value, productId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
