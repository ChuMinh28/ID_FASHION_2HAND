package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Order;


@Repository

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
