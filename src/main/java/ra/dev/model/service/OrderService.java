package ra.dev.model.service;

import ra.dev.dto.request.OrderCreate;
import ra.dev.dto.respone.OrderResponse;
import ra.dev.model.entity.Order;

import java.util.List;

public interface OrderService {
    boolean changeOrderStatus(int orderID, String action);
    OrderResponse getUserOrder();
    Order checkout(OrderCreate orderCreate);
}
