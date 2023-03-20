package ra.dev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.dev.model.entity.Catalog;
import ra.dev.model.entity.Image;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {
    private String productName;
    private String image;
    private String title;
    private String description;
    private int price;
    private boolean gender;
    private boolean limited;
    private boolean shipping;
    List<Catalog> listCatalog;
    List<Image> listImage = new ArrayList<>();


}
