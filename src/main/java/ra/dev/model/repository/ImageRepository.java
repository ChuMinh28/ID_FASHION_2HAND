package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Image;

public interface ImageRepository extends JpaRepository<Image,Integer> {
}
