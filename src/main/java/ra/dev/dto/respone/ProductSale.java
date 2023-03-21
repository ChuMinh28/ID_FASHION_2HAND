package ra.dev.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSale{
    private int productID;
    private String productName;
    private String image;
    private int price;
    private String color;
    private String size;
    private int discount;
}
