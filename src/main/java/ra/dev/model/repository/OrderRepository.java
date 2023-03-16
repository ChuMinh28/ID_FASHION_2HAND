package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
