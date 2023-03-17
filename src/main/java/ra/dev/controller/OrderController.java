package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.respone.OrderResponse;
import ra.dev.model.service.OrderService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("getUserOrder/{orderID}")
    public OrderResponse getUserOrder(@PathVariable("orderID") int orderID) {
        return orderService.getUserOrder(orderID);
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
