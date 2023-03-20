package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Collections;
import ra.dev.model.entity.Product;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collections, Integer> {
    List<Collections> findByCollectionNameContaining(String name);

    List<Collections> findByCollectionDescriptionContaining(String name);

}
