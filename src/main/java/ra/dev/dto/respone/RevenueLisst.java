package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RevenueLisst {
    private LocalDate date;
    private List<ProductRevenue> revenueList;
}
