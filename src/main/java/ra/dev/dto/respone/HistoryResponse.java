package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryResponse {
    private LocalDate dayUpdate;
    private int quantity;
    private String productName;
    private String action;
}
