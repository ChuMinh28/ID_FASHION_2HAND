package ra.dev.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.User;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    Page<User> searchUserByFullNameContainingIgnoreCase(String userName, Pageable pageable);
    List<User> findAllByCreatedBetween(LocalDate start, LocalDate end);
    @Query(value = "select w.productID from wishlist w", nativeQuery = true)
    List<Integer> favoriteProduct();
}
