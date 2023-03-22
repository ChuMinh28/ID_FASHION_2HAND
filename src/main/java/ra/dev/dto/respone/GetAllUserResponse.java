package ra.dev.dto.respone;

import lombok.Data;

@Data
public class GetAllUserResponse {
    private int userID;
    private String userName;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private int zipCode;
}
