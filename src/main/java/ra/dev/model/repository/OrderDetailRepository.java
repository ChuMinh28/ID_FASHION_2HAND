package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
}
