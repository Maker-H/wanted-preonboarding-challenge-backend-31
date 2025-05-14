package wanted.shop.user.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.user.domain.User;

public interface UserDataRepository extends JpaRepository<User, Long> {
}
