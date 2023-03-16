package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog,Integer> {
}
