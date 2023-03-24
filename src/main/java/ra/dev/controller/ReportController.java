package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.respone.NewUserByDays;
import ra.dev.dto.respone.NewUserHasOrder;
import ra.dev.dto.respone.UserResponse;
import ra.dev.model.service.OrderService;
import ra.dev.model.service.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("favoriteProduct")
    public ResponseEntity<?> favoriteProduct() {
        return ResponseEntity.ok(userService.favoriteProduct());
    }

    @GetMapping("getNewUserByDate")
    public ResponseEntity<?> getNewUserByDate(@RequestParam int days) {
        List<NewUserByDays> list = userService.newUserByDate(days);
        Map<String, Object> data = new HashMap<>();
        data.put("Account has been created", list.size());
        data.put("List account", list);
        return ResponseEntity.ok(data);
    }


    @GetMapping("newUserHasOrder")
    public ResponseEntity<?> getNewUserHasOrder(@RequestParam int days) {
        List<NewUserHasOrder> list = orderService.newUserHasOrder(days);
        Map<String, Object> data = new HashMap<>();
        data.put("Account has order", list.size());
        data.put("List account", list);
        return ResponseEntity.ok(data);
    }


    @GetMapping("revenueByDate")
    public ResponseEntity<?> getRevenueByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(orderService.getRevenueByDate(start, end));
    }


    @GetMapping("/revenuebyAddress")
    public ResponseEntity<?> revenueByAddresses(@RequestParam String address,
                                                @RequestParam String start,
                                                @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return orderService.getRevenueByAddress(address, startDate, endDate);
    }
}
