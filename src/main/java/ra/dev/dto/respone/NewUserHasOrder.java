package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class NewUserHasOrder {
    private int userID;
    private String userName;
    private String email;
    private LocalDate created;
    private String fullName;
    private String phoneNumber;
    private String address;
    private List<OrderRecentResponse> listOrder = new ArrayList<>();
}
