package ra.dev.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String address;
    private boolean userStatus;
    private Set<String> listRoles;


}
