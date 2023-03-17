package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findProductByListProductDetailContaining(ProductDetail productDetail);
    Product findProductByListProductDetailContainingAndGender(ProductDetail productDetail, Boolean status);

}
