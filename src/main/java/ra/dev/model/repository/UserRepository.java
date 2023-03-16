package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
