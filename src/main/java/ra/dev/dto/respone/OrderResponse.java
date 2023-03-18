package ra.dev.dto.respone;


import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private int totalAmount;
    private boolean shipping;
    List<OrderDetailResponse> listOrderDetail = new ArrayList<>();
}
