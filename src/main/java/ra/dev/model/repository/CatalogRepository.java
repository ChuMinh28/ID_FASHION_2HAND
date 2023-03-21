package ra.dev.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Catalog;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Integer> {
    Catalog findCatalogByCatalogName(String catalogName);
  Page<Catalog> findByCatalogNameContaining(String name, Pageable pageable);
}
