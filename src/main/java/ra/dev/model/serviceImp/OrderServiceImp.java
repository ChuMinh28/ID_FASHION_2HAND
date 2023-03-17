package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.OrderDetailResponse;
import ra.dev.dto.respone.OrderResponse;
import ra.dev.model.entity.Order;
import ra.dev.model.entity.OrderDetail;
import ra.dev.model.repository.OrderDetailRepository;
import ra.dev.model.repository.OrderRepository;
import ra.dev.model.service.OrderService;

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
            return false;
        }
    }

    @Override
    public OrderResponse getUserOrder(int orderID) {
        try {
            Order order = orderRepository.findById(orderID).get();
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderID(orderID);
            orderResponse.setFullName(order.getFullName());
            orderResponse.setEmail(order.getEmail());
            orderResponse.setOrderDate(order.getOrderDate());
            orderResponse.setAddress(order.getAddress());
            orderResponse.setTotalAmount(order.getTotalAmount());
            orderResponse.setOrderStatus(order.getOrderStatus());
            List<OrderDetail> list = orderDetailRepository.findAllByOrder_OrderID(orderID);
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
            }
            return orderResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
