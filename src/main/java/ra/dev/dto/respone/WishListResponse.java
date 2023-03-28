package ra.dev.dto.respone;

import lombok.Data;

@Data
public class WishListResponse {
    private String productName;
    private String image;
    private String title;
    private int price;
    private String limited;
}
