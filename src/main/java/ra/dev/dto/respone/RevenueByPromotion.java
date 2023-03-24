package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueByPromotion {
    private int countOrder;
    private int totalAmount;
}
