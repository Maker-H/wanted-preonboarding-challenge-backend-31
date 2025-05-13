package wanted.shop.user.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final UserDataRepository dataRepository;

    public User findById(UserId userId) {
        return dataRepository.findById(userId.getValue())
                .orElseThrow(() -> new RuntimeException("없는 user입니다"));
    }
}
