package ra.dev.model.service;

import ra.dev.dto.request.ProductByCat;
import ra.dev.dto.respone.*;
import ra.dev.model.entity.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    List<GetProduct> listSale();

    Map<String,Object> getPagging(int id, int number,String searchBy,String sortBy,String name,String direction,int page, int size);
    Set<GetProductByCat> findProductByCatalog(ProductByCat productByCat);
    List<RevenueLisst> getListRevenue(LocalDate start, LocalDate end);
    void exportFile(HttpServletResponse response) throws IOException;

}
