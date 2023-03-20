package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRecentResponse {
    private int orderID;
    private LocalDate created;
    private String orderStatus;
    private String paymentMethod;
    private int totalAmount;
}
