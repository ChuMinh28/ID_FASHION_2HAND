package ra.dev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCreate {
    private Integer userID;
    private Integer quantity;
    private Integer productID;
    private Integer colorID;
    private Integer sizeID;
}
