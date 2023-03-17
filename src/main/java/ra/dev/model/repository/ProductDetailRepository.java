package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.ProductDetail;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {
    List<ProductDetail> findProductDetailByColorColorID(int colorID);
    List<ProductDetail> findProductDetailBySizeSizeID(int sizeID);
    List<ProductDetail> findProductDetailByProductGender(Boolean status);
    List<ProductDetail> findProductDetailByColorColorIDAndProductGender(int colorID, Boolean status);
    List<ProductDetail> findProductDetailBySizeSizeIDAndProductGender(int sizeID, Boolean status);
    List<ProductDetail> findProductDetailByColorColorIDAndProductProductID(int colorID,int productID);
    List<ProductDetail> findProductDetailBySizeSizeIDAndProductProductID(int sizeID, int productID);
    ProductDetail findProductDetailBySizeSizeIDAndColorColorIDAndProductProductID(int sizeID, int colorID, int productID);
    List<ProductDetail> findProductDetailByProductLimited(Boolean status);


}
