package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.request.CartCreate;
import ra.dev.model.entity.OrderDetail;
import ra.dev.model.service.OrderDetailService;
import ra.dev.model.service.OrderService;
import ra.dev.model.service.UserService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    UserService userService;

   @PostMapping("addToCart")
    public OrderDetail addToCart(@RequestBody CartCreate cartCreate){
       return orderDetailService.createCart(cartCreate);
   }

}
