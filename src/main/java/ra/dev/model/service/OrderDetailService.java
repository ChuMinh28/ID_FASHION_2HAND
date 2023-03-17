package ra.dev.model.service;

import ra.dev.dto.request.CartCreate;
import ra.dev.model.entity.OrderDetail;

public interface OrderDetailService {
    OrderDetail createCart(CartCreate cartCreate);
}
