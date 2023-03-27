package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Order;
import ra.dev.model.entity.OrderDetail;
import ra.dev.model.entity.Product;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findAllByOrder_OrderID(int orderID);
    List<OrderDetail> findByOrderIn(List<Order> orderList);

}
