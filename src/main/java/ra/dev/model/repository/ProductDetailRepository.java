package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.ProductDetail;
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {
}
