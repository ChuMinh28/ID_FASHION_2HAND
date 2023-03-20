package ra.dev.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Order;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findOrderByOrderStatus(int orderStatus);
    Order findByOrderStatusAndUser_UserID(int orderStatus, int userID);
    List<Order> findAllByUser_UserID(int userID);
}
