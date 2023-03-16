package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
