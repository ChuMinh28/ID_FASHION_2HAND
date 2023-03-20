package ra.dev.model.service;

import ra.dev.dto.request.CreateProductDetail;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.entity.Size;

import java.util.List;
import java.util.Map;

public interface ProductDetailService {
    List<Size> getListSize(int colorID,int productID);
    List<Color> getListColor(int sizeID,int productID);
    ProductDetail getDetail(int sizeID, int ColorID, int productID);

    ProductDetail createProductDetail(CreateProductDetail createProductDetail);
    ProductDetail updateProductDetail(int productDetailID,CreateProductDetail createProductDetail);
    Map<String,Object> getPagging(int page, int size,String direction,String sortBy);

}
