package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.OrderDetail;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
}
