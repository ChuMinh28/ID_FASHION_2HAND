package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.model.service.OrderService;
import java.time.LocalDate;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    OrderService orderService;

    @GetMapping("/revenuebyAddress")
    public ResponseEntity<?> revenueByAddresses(@RequestParam String address,
                                                @RequestParam String start,
                                                @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return orderService.getRevenueByAddress(address, startDate, endDate);
    }

}
