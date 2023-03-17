package ra.dev.dto.respone;


import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private int orderID;
    private String fullName;
    private String email;
    private LocalDate orderDate;
    private String address;
    private int totalAmount;
    private int orderStatus;
    List<OrderDetailResponse> listOrderDetail = new ArrayList<>();
}
