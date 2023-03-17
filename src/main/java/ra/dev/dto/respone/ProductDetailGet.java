package ra.dev.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.Size;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailGet {
    private int productID;
    private String productName;
    private String image;
    private String title;
    private int price;
    List<Size> sizeList = new ArrayList<>();
    List<Color> colorList = new ArrayList<>();

}
