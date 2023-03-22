package ra.dev.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProductByCat {
    private int productId;
    private String productName;
    private String image;
    private String title;
    private int price;

}
