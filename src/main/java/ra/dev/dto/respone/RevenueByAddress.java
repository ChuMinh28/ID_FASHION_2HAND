package ra.dev.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueByAddress {
    private int id;
    private LocalDate dateOrder;
    private String address;
    private double revenue;
}
