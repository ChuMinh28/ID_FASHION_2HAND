package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Collection;

public interface CollectionRepository extends JpaRepository<Collection,Integer> {
}
