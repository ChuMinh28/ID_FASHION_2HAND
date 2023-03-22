package ra.dev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDetail {

    private int quantity;
    private int productID;
    private int sizeID;
    private int colorID;

}
