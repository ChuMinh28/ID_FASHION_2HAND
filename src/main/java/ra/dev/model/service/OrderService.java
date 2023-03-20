package ra.dev.model.service;


import org.springframework.data.domain.Pageable;
import ra.dev.dto.respone.OrderRecentResponse;
import ra.dev.dto.request.OrderCreate;
import ra.dev.dto.respone.OrderResponse;
import ra.dev.model.entity.Order;

import java.util.List;

import java.util.List;

public interface OrderService {
    boolean changeOrderStatus(int orderID, String action);
    OrderResponse getUserOrder();
    List<OrderRecentResponse> orderRecent(int size);
    Order checkout(OrderCreate orderCreate);
}
