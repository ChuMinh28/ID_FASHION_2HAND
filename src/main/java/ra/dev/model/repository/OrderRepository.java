package ra.dev.model.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Order;

import java.util.List;

@Repository

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findOrderByOrderStatus(int orderStatus);
}
