package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.OrderDetailResponse;
import ra.dev.dto.respone.OrderResponse;
import ra.dev.model.entity.Order;
import ra.dev.model.entity.OrderDetail;
import ra.dev.model.repository.OrderDetailRepository;
import ra.dev.model.repository.OrderRepository;
import ra.dev.model.service.OrderService;
import ra.dev.security.CustomUserDetails;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public boolean changeOrderStatus(int orderID, String action) {
        Order order = orderRepository.findById(orderID).get();
        try {
            if (action.equals("confirm")) {
                order.setOrderStatus(3);
                orderRepository.save(order);
            }
            if (action.equals("done")) {
                order.setOrderStatus(4);
                orderRepository.save(order);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderResponse getUserOrder() {
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Order order = orderRepository.findByOrderStatusAndUser_UserID(1, customUserDetail.getUserId());
            OrderResponse orderResponse = new OrderResponse();
            List<OrderDetail> list = orderDetailRepository.findAllByOrder_OrderID(order.getOrderID());
            int totalAmount = 0;
            for (OrderDetail orderDetail : list) {
                OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
                orderDetailResponse.setProductName(orderDetail.getProduct().getProductName());
                orderDetailResponse.setColor(orderDetail.getColor());
                orderDetailResponse.setSize(orderDetail.getSize());
                orderDetailResponse.setImage(orderDetail.getProduct().getImage());
                orderDetailResponse.setQuantity(orderDetail.getQuantity());
                orderDetailResponse.setPrice(orderDetail.getPrice());
                orderDetailResponse.setTotalAmount(orderDetail.getTotalAmount());
                orderResponse.getListOrderDetail().add(orderDetailResponse);
                totalAmount += orderDetailResponse.getTotalAmount();
            }
            orderResponse.setTotalAmount(totalAmount);
            orderResponse.setShipping(false);
            return orderResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
