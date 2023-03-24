package ra.dev.dto.respone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewUserByDays {
    private int userID;
    private String userName;
    private String email;
    private LocalDate created;
    private String fullName;
    private String phoneNumber;
    private String address;
}
