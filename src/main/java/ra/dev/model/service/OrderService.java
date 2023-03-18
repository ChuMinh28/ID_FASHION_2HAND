package ra.dev.model.service;

import ra.dev.dto.respone.OrderResponse;

public interface OrderService {
    boolean changeOrderStatus(int orderID, String action);
    OrderResponse getUserOrder();
}
