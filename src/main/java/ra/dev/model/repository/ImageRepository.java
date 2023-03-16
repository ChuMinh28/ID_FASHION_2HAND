package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Image;
@Repository

public interface ImageRepository extends JpaRepository<Image,Integer> {
}
