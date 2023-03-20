package ra.dev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreate {
    private String address;
    private String email;
    private String fullName;
    private LocalDate date;
    private int totalAmout;
    private int userID;
}
