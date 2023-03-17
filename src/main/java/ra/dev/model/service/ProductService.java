package ra.dev.model.service;

import ra.dev.dto.respone.GetProduct;
import ra.dev.dto.respone.ProductSale;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;

import java.util.List;

public interface ProductService {
    List<GetProduct> getAll();
    List<GetProduct> sortAndFilter(String direction, String color, String size);
    List<ProductSale> getBestSale();
    List<GetProduct> getByGender(String direction, String color, String size, String sex);

    List<GetProduct> getByFilter(String color, String size);
    List<GetProduct> getProductLimited();

}
