package ra.dev.model.service;

import ra.dev.dto.request.CreateProduct;
import ra.dev.dto.respone.GetProduct;
import ra.dev.dto.respone.ProductDetailGet;
import ra.dev.dto.respone.ProductSale;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;

import ra.dev.model.entity.Catalog;
import ra.dev.model.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<GetProduct> getAll();
    List<GetProduct> sortAndFilter(String direction, String color, String size);
    List<ProductSale> getBestSale();
    List<GetProduct> getByGender(String direction, String color, String size, String sex);
    List<GetProduct> getByFilter(String color, String size);
    List<GetProduct> getProductLimited();
    ProductDetailGet getDetail(int productID);
    Product createProduct(Product createProduct);
    Product updateProduct(int productID, Product updateProduct);

    Map<String,Object> getPagging(int id, int number,String searchBy,String sortBy,String pagination,String name,String direction,int page, int size);


}
