package ra.dev.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.dev.model.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles,Integer> {
}
