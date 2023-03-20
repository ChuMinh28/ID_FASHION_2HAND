package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Catalog;
@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Integer> {
    Catalog findCatalogByCatalogName(String catalogName);
}
