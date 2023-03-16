package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
