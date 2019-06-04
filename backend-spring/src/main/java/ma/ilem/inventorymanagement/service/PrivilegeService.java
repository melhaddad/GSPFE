package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Privilege;
import ma.ilem.inventorymanagement.pojo.PrivilegeName;
import org.springframework.data.history.Revisions;

import java.util.List;

public interface PrivilegeService {
    void init();

    Privilege findByName(PrivilegeName name);

    List<Privilege> findAll();

    void update(Privilege privilege, Long id);

    void enable(boolean active, Long id);

    List<Revisions<Integer, Privilege>> findAllHistory();
}
