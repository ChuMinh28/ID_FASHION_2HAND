package ra.dev.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findOrderByOrderStatus(int orderStatus);
    Order findByOrderStatusAndUser_UserID(int orderStatus, int userID);
    List<Order> findAllByUser_UserID(int userID);
    Page<Order> findOrderByOrderDateBetween(LocalDate start,LocalDate end,Pageable pageable);
   Page<Order> findByOrOrderStatus(int status,Pageable pageable);
   Page<Order> findByAddressContaining(Pageable pageable,String name);
   List<Order> findByOrderStatusAndAddressEqualsAndOrderDateBetween(Integer status,String address,LocalDate start,LocalDate end);


}
