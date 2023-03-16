package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Collections;
@Repository
public interface CollectionRepository extends JpaRepository<Collections,Integer> {

}
