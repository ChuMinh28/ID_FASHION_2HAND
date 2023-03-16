package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {
}
