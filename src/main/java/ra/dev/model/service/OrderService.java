package ra.dev.model.service;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ra.dev.dto.respone.NewUserHasOrder;
import ra.dev.dto.respone.OrderRecentResponse;
import ra.dev.dto.request.OrderCreate;
import ra.dev.dto.respone.OrderResponse;

import ra.dev.dto.respone.UserResponse;
import ra.dev.model.entity.Order;
import ra.dev.model.entity.User;

import java.time.LocalDate;
import java.util.List;

import java.util.Map;

public interface OrderService {
    boolean changeOrderStatus(int orderID, String action);

    OrderResponse getUserOrder();

    List<OrderRecentResponse> orderRecent(int size);

    Order checkout(OrderCreate orderCreate);

    Map<String, Object> getPagging(int number, String searchBy, String name, String sortBy, String direction, int page, int size);

    Map<String, Object> findByDate(LocalDate start, LocalDate end, Pageable pageable);

    Map<LocalDate, Object> getRevenueByDate(LocalDate start, LocalDate end);

    ResponseEntity<?> getRevenueByAddress(String address, LocalDate start, LocalDate end);
    List<NewUserHasOrder> newUserHasOrder(int days);
    boolean cancelOrder(int orderID);
}
