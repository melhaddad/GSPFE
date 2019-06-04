package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.Privilege;
import ma.ilem.inventorymanagement.pojo.PrivilegeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends RevisionRepository<Privilege, Long, Integer>, JpaRepository<Privilege, Long> {
    Privilege findByName(PrivilegeName name);
}
