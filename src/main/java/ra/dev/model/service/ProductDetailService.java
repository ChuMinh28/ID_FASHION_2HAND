package ra.dev.model.service;

import ra.dev.model.entity.Color;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.entity.Size;

import java.util.List;

public interface ProductDetailService {
    List<Size> getListSize(int colorID,int productID);
    List<Color> getListColor(int sizeID,int productID);

    ProductDetail getDetail(int sizeID, int ColorID, int productID);

}
