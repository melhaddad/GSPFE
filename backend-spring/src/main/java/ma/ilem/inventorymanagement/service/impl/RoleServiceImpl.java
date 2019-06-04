package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Role;
import ma.ilem.inventorymanagement.exception.RoleBadActionException;
import ma.ilem.inventorymanagement.exception.RoleDuplicateNameException;
import ma.ilem.inventorymanagement.exception.RoleNotFoundException;
import ma.ilem.inventorymanagement.pojo.PrivilegeName;
import ma.ilem.inventorymanagement.repository.RoleRepository;
import ma.ilem.inventorymanagement.service.PrivilegeService;
import ma.ilem.inventorymanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeService privilegeService;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByNameAndDeletedIsFalse(name);
    }

    @Override
    public void init() {
        saveAdminIfNotFound();
        saveUserIfNotFound();
    }

    @Transactional
    public void saveUserIfNotFound() {
        Role role = roleRepository.findByNameAndDeletedIsFalse("USER");

        if (role == null) {
            role = new Role();

            role.setName("USER");

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.SHOW_PROFILE)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.UPDATE_PROFILE_IMAGE)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.UPDATE_PASSWORD)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.RETURN_ITEM)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.REQUEST_ITEM)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.SHOW_ITEMS)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.SHOW_CATEGORIES)
            );

            role.getPrivileges().add(
                    privilegeService.findByName(PrivilegeName.SHOW_USER_ITEMS)
            );

            roleRepository.save(role);
        }
    }

    @Transactional
    public void saveAdminIfNotFound() {
        Role role = roleRepository.findByNameAndDeletedIsFalse("ADMIN");

        if (role == null) {
            role = new Role();

            role.setName("ADMIN");

            role.getPrivileges().add(privilegeService.findByName(PrivilegeName.ALL_PRIVILEGES));

            roleRepository.save(role);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role save(Role role) {
        Role exist = roleRepository.findByNameAndDeletedIsFalse(role.getName());

        if (exist != null) {
            throw new RoleDuplicateNameException("Il existe déjà un rôle avec ce nom.");
        }

        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void update(Long id, Role role) {
        Optional<Role> optional = roleRepository.findByIdAndDeletedIsFalse(id);

        if (!optional.isPresent()) {
            throw new RoleNotFoundException("Le rôle n'existe pas.");
        }

        if (optional.get().getName() == "ADMIN") {
            throw new RoleBadActionException("Action impossible sur ce rôle");
        }

        Role name = roleRepository.findByNameAndDeletedIsFalse(role.getName());

        if (name != null && name.getId() != optional.get().getId()) {
            throw new RoleDuplicateNameException("Il existe déjà un rôle avec ce nom.");
        }

        Role exist = optional.get();

        exist.setName(role.getName());
        exist.getPrivileges().clear();
        exist.getPrivileges().addAll(role.getPrivileges());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Role> exist = roleRepository.findByIdAndDeletedIsFalse(id);

        if (!exist.isPresent()) {
            throw new RoleNotFoundException("Le rôle n'existe pas.");
        }

        if (exist.get().getName() == "ADMIN") {
            throw new RoleBadActionException("Action impossible sur ce rôle");
        }

        Role role = exist.get();

        role.setName("@" + role.getName() + " " + new Date().getTime());
        role.setDeleted(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long roleId) {
        return roleRepository.findByIdAndDeletedIsFalse(roleId).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllByDeletedIsFalse() {
        return roleRepository.findAllByDeletedIsFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revisions<Integer, Role>> findAllHistory() {
        List<Revisions<Integer, Role>> resutl = new ArrayList<>();

        List<Role> roles = roleRepository.findAll();

        roles.forEach(role -> {
            Revisions<Integer, Role> revisions = roleRepository.findRevisions(role.getId());

            resutl.add(revisions);
        });

        return resutl;
    }
}
