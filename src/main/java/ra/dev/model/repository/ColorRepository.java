package ra.dev.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Color;
@Repository
public interface ColorRepository extends JpaRepository<Color,Integer> {
    Color findColorByColorName(String colorName);
    Page<Color> findByColorNameContaining(String name, Pageable pageable);
}
