package ra.dev.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.dev.model.entity.Image;
import ra.dev.model.entity.Product;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionGet {
    private int collectionID;
    private String collectionName;
    private boolean collectionStatus;

    List<Product> productList = new ArrayList<>();
}
