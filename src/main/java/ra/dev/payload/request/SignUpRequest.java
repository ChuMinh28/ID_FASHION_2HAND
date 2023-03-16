package ra.dev.payload.request;


import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String fullName;
    private String PhoneNumber;
    private String address;
    private int zipCode;
    private Set<String> listRoles;
}
