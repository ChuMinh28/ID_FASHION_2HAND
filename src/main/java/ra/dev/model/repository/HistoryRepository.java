package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.HistoryUpdateProduct;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryUpdateProduct,Integer> {
    List<HistoryUpdateProduct> findAllByProductDetail_ProductDetailID(int productID);
}
