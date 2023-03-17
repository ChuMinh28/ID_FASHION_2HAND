package ra.dev.dto.respone;

import lombok.Data;
import ra.dev.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private String userName;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private int zipCode;
    private List<Order> listOrder = new ArrayList<>();
}
