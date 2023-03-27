package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.OrderCreate;
import ra.dev.dto.respone.OrderDetailResponse;
import ra.dev.dto.respone.OrderRecentResponse;
import ra.dev.dto.respone.OrderResponse;

import ra.dev.dto.respone.RevenueByPromotion;
import ra.dev.dto.respone.*;

import ra.dev.model.entity.Order;
import ra.dev.model.entity.OrderDetail;
import ra.dev.model.entity.User;

import ra.dev.model.repository.OrderDetailRepository;
import ra.dev.model.repository.OrderRepository;
import ra.dev.model.repository.UserRepository;
import ra.dev.model.service.OrderService;
import ra.dev.security.CustomUserDetails;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalDate;
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

        try {
            Order order = orderRepository.findByOrderStatusAndUser_UserID(1, customUserDetail.getUserId());
            OrderResponse orderResponse = new OrderResponse();
            List<OrderDetail> list = orderDetailRepository.findAllByOrder_OrderID(order.getOrderID());
            int totalAmount = 0;
            for (OrderDetail orderDetail : list) {
                OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
                orderDetailResponse.setOrderDetailID(orderDetail.getOrderDetailID());
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
                orderRecentResponse.setOrderID(order.getOrderID());
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
        int price = 0;
        boolean checkOrder = false;
        Order newOrder = new Order();
        for (Order orserST1 : listOrder) {
            if (orserST1.getUser().getUserID() == orderCreate.getUserID()) {
                checkOrder = true;
                for (OrderDetail o : orserST1.getListOrderDetail()) {
                    price += o.getTotalAmount();
                }
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
            newOrder.setTotalAmount(price);
            newOrder.setUser(users);
            orderRepository.save(newOrder);
        }
        return newOrder;
    }

    public Map<String, Object> getPaggination(Page<Order> orderPage) {
        Map<String, Object> data = new HashMap<>();
        data.put("Order in page", orderPage.getContent());
        data.put("Total elements in Catalog", orderPage.getTotalElements());
        data.put("Total page in Catalog", orderPage.getTotalPages());
        data.put("Size", orderPage.getSize());
        return data;
    }

    @Override
    public Map<String, Object> getPagging(int number, String searchBy, String name, String sortBy, String direction, int page, int size) {
        if (searchBy.equals("0") && sortBy.equals("0")) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Order> orderPage = orderRepository.findAll(pageable);
            return getPaggination(orderPage);
        } else if (searchBy != "0") {
            Pageable pageable = PageRequest.of(page, size);
            Page<Order> orderPage;
            if (searchBy.equals("status")) {
                orderPage = orderRepository.findByOrOrderStatus(number, pageable);
            } else {
                orderPage = orderRepository.findByAddressContaining(pageable, name);
            }
            return getPaggination(orderPage);
        } else if (sortBy != "0") {
            Pageable pageable;
            if (sortBy.equals("date")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("OrderDate").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("OrderDate").descending());
                }
            } else if (sortBy.equalsIgnoreCase("totalamount")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("TotalAmount").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("TotalAmount").descending());
                }
            } else {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("OrderStatus").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("OrderStatus").descending());
                }
            }
            Page<Order> orderPage = orderRepository.findAll(pageable);
            return getPaggination(orderPage);
        } else {
            Pageable pageable;
            if (sortBy.equals("date")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("OrderDate").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("OrderDate").descending());
                }
            } else if (sortBy.equalsIgnoreCase("totalamount")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("TotalAmount").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("TotalAmount").descending());
                }
            } else {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("OrderStatus").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("OrderStatus").descending());
                }
            }
            Page<Order> orderPage;
            if (searchBy.equals("status")) {
                orderPage = orderRepository.findByOrOrderStatus(number, pageable);
            } else {
                orderPage = orderRepository.findByAddressContaining(pageable, name);
            }
            return getPaggination(orderPage);
        }

    }


    @Override
    public Map<String, Object> findByDate(LocalDate start, LocalDate end, Pageable pageable) {
        Page<Order> orders = orderRepository.findOrderByOrderDateBetween(start, end, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("Order", orders.getContent());
        data.put("TotalElement", orders.getTotalElements());
        data.put("Size", orders.getSize());
        data.put("TotalPage", orders.getTotalPages());
        return data;
    }

    @Override
    public Map<LocalDate, Object> getRevenueByDate(LocalDate start, LocalDate end) {
        List<Order> listOrderComplete = orderRepository.findOrderByOrderDateBetweenAndOrderStatus(start,end,4);
        Map<LocalDate, Object> mapOrder = new HashMap<>();
        for (Order order : listOrderComplete) {
            int totalOrder = 1;
            int totalAmount = order.getTotalAmount();
            RevenueByPromotion revenueByPromotion = new RevenueByPromotion();
            revenueByPromotion.setTotalAmount(totalAmount);
            revenueByPromotion.setCountOrder(totalOrder);
            LocalDate key = order.getOrderDate();
            if (mapOrder.containsKey(key)) {
                RevenueByPromotion oldOrder = (RevenueByPromotion) mapOrder.get(key);
                totalOrder += oldOrder.getCountOrder();
                totalAmount += oldOrder.getTotalAmount();
                revenueByPromotion.setTotalAmount(totalAmount);
                revenueByPromotion.setCountOrder(totalOrder);
                mapOrder.put(key, revenueByPromotion);
            } else {
                mapOrder.put(key, revenueByPromotion);
            }
        }
        return mapOrder;
    }


    @Override
    public ResponseEntity<?> getRevenueByAddress(String address, LocalDate start, LocalDate end) {
        List<Order> orderList = orderRepository.findByOrderStatusAndAddressEqualsAndOrderDateBetween(4, address, start, end);
        List<RevenueByAddress> addressList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(start,end);
        for (int i = 0; i <=daysBetween ; i++) {
            RevenueByAddress revenue=new RevenueByAddress();
            revenue.setId(i+1);
            revenue.setDateOrder(start.plusDays(i));
            revenue.setAddress(address);
            revenue.setRevenue(0);
            for (Order o:orderList ) {
                if (o.getOrderDate().equals(revenue.getDateOrder())){
                    revenue.setRevenue(revenue.getRevenue()+o.getTotalAmount());

                }
            }
            addressList.add(revenue);
        }
        return ResponseEntity.ok(addressList);
    }

    @Override
    public List<NewUserHasOrder> newUserHasOrder(int days) {
        try {
            LocalDate end = LocalDate.now();
            LocalDate start = end.minusDays(days);
            List<User> listUser = userRepository.findAllByCreatedBetween(start,end);
            List<NewUserHasOrder> list = new ArrayList<>();
            for (User user:listUser) {
                List<Order> listOrder = orderRepository.findAllByUser_UserID(user.getUserID());
                if (!listOrder.isEmpty()) {
                    NewUserHasOrder userResponse = new NewUserHasOrder();
                    userResponse.setUserID(user.getUserID());
                    userResponse.setUserName(user.getUserName());
                    userResponse.setEmail(user.getEmail());
                    userResponse.setCreated(user.getCreated());
                    userResponse.setFullName(user.getFullName());
                    userResponse.setPhoneNumber(user.getPhoneNumber());
                    userResponse.setAddress(user.getAddress());
                    for (Order order:user.getListOrder()) {
                        if (order.getOrderStatus()!=1) {
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
                            orderRecentResponse.setOrderID(order.getOrderID());
                            orderRecentResponse.setPaymentMethod("Cash");
                            orderRecentResponse.setTotalAmount(order.getTotalAmount());
                            userResponse.getListOrder().add(orderRecentResponse);
                        }
                    }
                    list.add(userResponse);
                }
            }
            List<NewUserHasOrder> listResponse = list.stream()
                    .sorted(Comparator.comparing(NewUserHasOrder::getCreated).reversed())
                    .collect(Collectors.toList());
            return listResponse;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



//    public int getTotalRevenue(List<Order> orderList) {
//        int revenue = 0;
//        for (Order o : orderList) {
//            revenue += o.getTotalAmount();
//        }
//        return revenue;
//    }
}
