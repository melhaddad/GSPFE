package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends RevisionRepository<Role, Long, Integer>, JpaRepository<Role, Long> {
    Role findByNameAndDeletedIsFalse(String name);

    List<Role> findAllByDeletedIsFalse();

    Optional<Role> findByIdAndDeletedIsFalse(Long aLong);
}
