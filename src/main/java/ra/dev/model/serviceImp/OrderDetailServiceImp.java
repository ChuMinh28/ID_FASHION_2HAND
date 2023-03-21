package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.CartCreate;
import ra.dev.dto.request.OrderRequest;
import ra.dev.model.entity.*;
import ra.dev.model.repository.*;
import ra.dev.model.service.OrderDetailService;
import ra.dev.model.service.OrderService;

import java.util.List;

@Service
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;
    @Override
    public OrderDetail createCart(CartCreate cartCreate) {
        OrderDetail orderDetail = new OrderDetail();
        User user = userRepository.findById(cartCreate.getUserID()).get();
        List<Order> orderList = orderRepository.findOrderByOrderStatus(1);
        boolean checkOrder = false;
        Order newOrder = new Order();
        for(Order order : orderList){
            if(order.getUser().getUserID()==cartCreate.getUserID()){
                checkOrder = true;
                newOrder = order;
                break;
            }
        }
        if(checkOrder){
            Product product = productRepository.findById(cartCreate.getProductID()).get();
            ProductDetail productDetail = productDetailRepository.findProductDetailBySizeSizeIDAndColorColorIDAndProductProductID(cartCreate.getSizeID(),cartCreate.getColorID(),cartCreate.getProductID());
            boolean check = false;
            OrderDetail orderDetailUpdate =new OrderDetail();
            for (OrderDetail orderDetail1 : newOrder.getListOrderDetail()) {
                if(orderDetail1.getProduct().getProductID()==product.getProductID()){
                    check = true;
                    orderDetailUpdate = orderDetail1;
                    break;
                }
            }
            if(check){
                orderDetailUpdate.setOrder(newOrder);
                orderDetailUpdate.setProduct(product);
                orderDetailUpdate.setQuantity(orderDetailUpdate.getQuantity()+ cartCreate.getQuantity());
                orderDetailUpdate.setTotalAmount(orderDetailUpdate.getTotalAmount()+cartCreate.getQuantity()*(product.getPrice()*100-product.getDiscount())/100);
                orderDetailUpdate.setColor(productDetail.getColor().getColorName());
                orderDetailUpdate.setSize(productDetail.getSize().getSizeName());
                orderDetailUpdate.setPrice(product.getPrice());
                return orderDetailRepository.save(orderDetailUpdate);
            }else {
                orderDetail.setOrder(newOrder);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(cartCreate.getQuantity());
                orderDetail.setTotalAmount(cartCreate.getQuantity()*(product.getPrice()*100-product.getDiscount())/100);
                orderDetail.setColor(productDetail.getColor().getColorName());
                orderDetail.setSize(productDetail.getSize().getSizeName());
                orderDetail.setPrice(product.getPrice());
                orderDetailRepository.save(orderDetail);

            }
        }else {
            Order order = new Order();
            order.setUser(user);
            order.setOrderStatus(1);
            Order orderCreate = orderRepository.save(order);
            Product product = productRepository.findById(cartCreate.getProductID()).get();
            ProductDetail productDetail = productDetailRepository.findProductDetailBySizeSizeIDAndColorColorIDAndProductProductID(cartCreate.getSizeID(),cartCreate.getColorID(),cartCreate.getProductID());
            orderDetail.setOrder(orderCreate);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartCreate.getQuantity());
            orderDetail.setTotalAmount(cartCreate.getQuantity()*(product.getPrice()*100-product.getDiscount())/100);
            orderDetail.setColor(productDetail.getColor().getColorName());
            orderDetail.setSize(productDetail.getSize().getSizeName());
            orderDetail.setPrice(product.getPrice());
            orderDetailRepository.save(orderDetail);
        }
        return orderDetail;
    }

    @Override
    public boolean deleteProductFromCart(int orderDetailID) {
        try {
            orderDetailRepository.deleteById(orderDetailID);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCart(int orderDetailID, OrderRequest orderRequest) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findById(orderDetailID).get();
            orderDetail.setQuantity(orderRequest.getQuantity());
            int totalAmount = orderDetail.getPrice()*orderRequest.getQuantity();
            orderDetail.setTotalAmount(totalAmount);
            orderDetailRepository.save(orderDetail);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
