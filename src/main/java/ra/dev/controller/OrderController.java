package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.request.OrderRequest;
import ra.dev.dto.respone.OrderResponse;

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

   @DeleteMapping("deleteFromCart/{orderDetailID}")
   public ResponseEntity<?> deleteFromCart(@PathVariable("orderDetailID") int orderDetailID) {
       boolean check = orderDetailService.deleteProductFromCart(orderDetailID);
       if (check) {
           return ResponseEntity.ok("Delete successfully!");
       } else {
           return ResponseEntity.badRequest().body("Action failed!");
       }
   }

   @PatchMapping("updateProductFromCart/{orderDetailID}")
   public ResponseEntity<?> updateProductFromCart(@PathVariable("orderDetailID") int orderDetailID, @RequestBody OrderRequest orderRequest) {
       boolean check = orderDetailService.updateCart(orderDetailID,orderRequest);
       if (check) {
           return ResponseEntity.ok("Update successfully!");
       } else {
           return ResponseEntity.badRequest().body("Action failed!");
       }
   }

    @GetMapping("getUserOrder")
    public ResponseEntity<?> getUserOrder() {
       OrderResponse orderResponse = orderService.getUserOrder();
       if (orderResponse!=null) {
           return ResponseEntity.ok(orderResponse);
       } else {
           return ResponseEntity.ok("Cart is empty!");
       }
    }

    @PatchMapping("changeOrderStatus/{orderID}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable("orderID") int orderID, @RequestParam("action") String action) {
        boolean check = orderService.changeOrderStatus(orderID,action);
        if (check) {
            return ResponseEntity.ok("Action successfully!");
        } else {
            return ResponseEntity.badRequest().body("Action failed!");
        }
    }
}
