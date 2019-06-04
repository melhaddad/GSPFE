package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Role;
import org.springframework.data.history.Revisions;

import java.util.List;

public interface RoleService {
    void init();

    Role findByName(String name);

    List<Role> findAll();

    Role save(Role role);

    void update(Long id, Role role);

    void delete(Long id);

    Role findById(Long roleId);

    List<Role> findAllByDeletedIsFalse();

    List<Revisions<Integer, Role>> findAllHistory();
}
