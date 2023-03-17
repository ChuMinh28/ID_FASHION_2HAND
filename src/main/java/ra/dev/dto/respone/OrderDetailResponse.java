package ra.dev.dto.respone;


import lombok.Data;

@Data
public class OrderDetailResponse {
    private String productName;
    private int quantity;
    private int price;
    private int totalAmount;
    private String color;
    private String size;
    private String image;
}
