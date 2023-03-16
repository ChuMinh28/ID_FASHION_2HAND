package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Color;

public interface ColorRepository extends JpaRepository<Color,Integer> {
}
