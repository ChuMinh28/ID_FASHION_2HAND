package ra.dev.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Collections;
import ra.dev.model.entity.Product;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collections, Integer> {
    Page<Collections> findByCollectionNameContaining(String name, Pageable pageable);


}
