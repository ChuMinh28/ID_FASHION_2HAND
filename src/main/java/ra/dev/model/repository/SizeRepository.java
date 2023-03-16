package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Size;
@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
}
