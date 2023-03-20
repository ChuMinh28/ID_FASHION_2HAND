package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.OrderCreate;
import ra.dev.dto.respone.OrderDetailResponse;
import ra.dev.dto.respone.OrderRecentResponse;
import ra.dev.dto.respone.OrderResponse;
import ra.dev.dto.respone.UserResponse;
import ra.dev.model.entity.Order;
import ra.dev.model.entity.OrderDetail;
import ra.dev.model.entity.User;
import ra.dev.model.repository.OrderDetailRepository;
import ra.dev.model.repository.OrderRepository;
import ra.dev.model.repository.UserRepository;
import ra.dev.model.service.OrderService;
import ra.dev.security.CustomUserDetails;


import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;

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
    }

    @Override
    public List<OrderRecentResponse> orderRecent(int size) {
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> listOrder = orderRepository.findAllByUser_UserID(customUserDetail.getUserId());
        List<OrderRecentResponse> list = new ArrayList<>();
        for (Order order : listOrder) {
            if (order.getOrderStatus() != 1) {
                OrderRecentResponse orderRecentResponse = new OrderRecentResponse();
                orderRecentResponse.setOrderID(order.getOrderID());
                orderRecentResponse.setCreated(order.getOrderDate());
                if (order.getOrderStatus() == 2) {
                    orderRecentResponse.setOrderStatus("Pending");
                }
                if (order.getOrderStatus() == 3) {
                    orderRecentResponse.setOrderStatus("Confirmed");
                }
                if (order.getOrderStatus() == 4) {
                    orderRecentResponse.setOrderStatus("Complete");
                }
                orderRecentResponse.setPaymentMethod("Cash");
                orderRecentResponse.setTotalAmount(order.getTotalAmount());
                list.add(orderRecentResponse);
            }
        }
        List<OrderRecentResponse> listResponse = list.stream()
                .sorted(Comparator.comparing(OrderRecentResponse::getCreated).reversed())
                .limit(size)
                .collect(Collectors.toList());
        return listResponse;
    }

    @Override
    public Order checkout(OrderCreate orderCreate) {
        User users = userRepository.findById(orderCreate.getUserID()).get();
        List<Order> listOrder = orderRepository.findOrderByOrderStatus(1);
        boolean checkOrder = false;
        Order newOrder = new Order();
        for (Order orserST1 : listOrder) {
            if (orserST1.getUser().getUserID() == orderCreate.getUserID()) {
                checkOrder = true;
                newOrder = orserST1;
                break;
            }
        }
        if (checkOrder) {
            newOrder.setOrderDate(LocalDate.now());
            newOrder.setAddress(orderCreate.getAddress());
            newOrder.setEmail(orderCreate.getEmail());
            newOrder.setOrderStatus(2);
            newOrder.setFullName(orderCreate.getFullName());
            newOrder.setTotalAmount(orderCreate.getTotalAmout());
            newOrder.setUser(users);
            orderRepository.save(newOrder);
        }
        return newOrder;
    }
}
