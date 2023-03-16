package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
