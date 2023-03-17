package ra.dev.model.service;

import ra.dev.dto.request.CartCreate;
import ra.dev.dto.request.OrderRequest;
import ra.dev.model.entity.OrderDetail;

public interface OrderDetailService {
    OrderDetail createCart(CartCreate cartCreate);
    boolean deleteProductFromCart(int orderDetailID);
    boolean updateCart(int orderDetailID, OrderRequest orderRequest);
}
