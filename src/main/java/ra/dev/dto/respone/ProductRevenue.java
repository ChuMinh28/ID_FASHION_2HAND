package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductRevenue {
    private int productID;
    private LocalDate date;
    private String ProductName;
    private int totalAmout;


}
