package ra.dev.dto.respone;

import lombok.Data;
import ra.dev.model.entity.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private int userID;
    private String userName;
    private String email;
    private LocalDate created;
    private String fullName;
    private String phoneNumber;
    private String address;
    private int zipCode;
    private List<Order> listOrder = new ArrayList<>();
}
