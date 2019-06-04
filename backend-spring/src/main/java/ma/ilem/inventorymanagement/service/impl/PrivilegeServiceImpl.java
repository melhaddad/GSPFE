package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Privilege;
import ma.ilem.inventorymanagement.exception.PrivilegeBadActionException;
import ma.ilem.inventorymanagement.exception.PrivilegeNotFoundException;
import ma.ilem.inventorymanagement.pojo.PrivilegeName;
import ma.ilem.inventorymanagement.repository.PrivilegeRepository;
import ma.ilem.inventorymanagement.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeRepository repository;

    @Override
    @Transactional
    public void init() {
        saveIfNotFound(PrivilegeName.ALL_PRIVILEGES, "Tous les privilèges", "Tous les privilèges", true);
        saveIfNotFound(PrivilegeName.SHOW_PROFILE, "Afficher le profile", "Profile", false);
        saveIfNotFound(PrivilegeName.UPDATE_PASSWORD, "Changer son mot de passe", "Changer", false);
        saveIfNotFound(PrivilegeName.UPDATE_PROFILE_IMAGE, "Changer son image", "Changer", false);
        saveIfNotFound(PrivilegeName.UPDATE_PASSWORDS, "Changer les mot de passes", "Changer mot de passe", false);
        saveIfNotFound(PrivilegeName.SHOW_CATEGORIES, "Afficher les catégories", "Catégories", false);
        saveIfNotFound(PrivilegeName.ADD_CATEGORY, "Ajouter une catégorie", "Nouveau", false);
        saveIfNotFound(PrivilegeName.UPDATE_CATEGORY, "Modifier un catégorie", "Modifier", false);
        saveIfNotFound(PrivilegeName.DELETE_CATEGORY, "Supprimer une catégorie", "Supprimer", false);
        saveIfNotFound(PrivilegeName.SHOW_ITEMS, "Afficher les matériels", "Matériels", false);
        saveIfNotFound(PrivilegeName.ADD_ITEM, "Ajouter un matériel", "Nouveau", false);
        saveIfNotFound(PrivilegeName.UPDATE_ITEM, "Modifier un matériel", "Modifier", false);
        saveIfNotFound(PrivilegeName.SUPPLY_ITEM, "Alimenter un matériel", "Alimenter", false);
        saveIfNotFound(PrivilegeName.DELETE_ITEM, "Supprimer un matériel", "Supprimer", false);
        saveIfNotFound(PrivilegeName.SHOW_USERS, "Afficher les employés", "Employés", false);
        saveIfNotFound(PrivilegeName.ADD_USER, "Ajouter un employé", "Nouveau", false);
        saveIfNotFound(PrivilegeName.UPDATE_USER, "Modifier un employé", "Modifier", false);
        saveIfNotFound(PrivilegeName.DELETE_USER, "Supprimer un employé", "Supprimer", false);
        saveIfNotFound(PrivilegeName.SHOW_ROLES, "Afficher les rôles", "Rôles", false);
        saveIfNotFound(PrivilegeName.ADD_ROLE, "Ajouter un rôle", "Nouveau", false);
        saveIfNotFound(PrivilegeName.UPDATE_ROLE, "Modifier un rôle", "Modifier", false);
        saveIfNotFound(PrivilegeName.DELETE_ROLE, "Supprimer un rôle", "Supprimer", false);
        saveIfNotFound(PrivilegeName.UPDATE_USER_ROLE, "Modifier les rôles des employés", "Rôles", false);
        saveIfNotFound(PrivilegeName.SHOW_PRIVILEGES, "Afficher les privilèges", "Privilèges", false);
        saveIfNotFound(PrivilegeName.ENABLE_PRIVILEGE, "Activer un privilège", "Activer", false);
        saveIfNotFound(PrivilegeName.UPDATE_PRIVILEGE, "Modifier un privilège", "Modifier", false);
        saveIfNotFound(PrivilegeName.DISABLE_PRIVILEGE, "Désactiver un privilège", "Désactiver", false);
        saveIfNotFound(PrivilegeName.SHOW_HISTORY, "Afficher les historiques", "Historiques", false);
        saveIfNotFound(PrivilegeName.SHOW_USER_ITEMS, "Afficher les affectations", "Affectations", false);
        saveIfNotFound(PrivilegeName.REQUEST_ITEM, "Demander un matériel", "Demander", false);
        saveIfNotFound(PrivilegeName.ACCEPT_ITEM, "Accepter une demande de matériel", "Accepter", false);
        saveIfNotFound(PrivilegeName.REFUSE_ITEM, "Refuser une demande de matériel", "Refuser", false);
        saveIfNotFound(PrivilegeName.DELIVER_ITEM, "Livrer un matériel accepter", "Livrer", false);
        saveIfNotFound(PrivilegeName.RETURN_ITEM, "Retourner un matériel livrer", "Retourner", false);
        saveIfNotFound(PrivilegeName.TAKE_ITEM, "Récupérer un matériel retourner", "Récupérer", false);
        saveIfNotFound(PrivilegeName.RECEIVE_STOCK_NOTIFICATIONS, "Recoit les notification du stock", "Notifications", false);
    }

    @Transactional
    public void saveIfNotFound(PrivilegeName name, String description, String show, boolean active) {
        Privilege privilege = repository.findByName(name);

        if (privilege == null) {
            repository.save(
                    new Privilege(null, name, description, show, active, null, false, null, null)
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Privilege findByName(PrivilegeName name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Privilege> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void update(Privilege privilege, Long id) {
        Optional<Privilege> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new PrivilegeNotFoundException("Le privilège n'existe pas.");
        }

        if (privilege.getName().equals(PrivilegeName.ALL_PRIVILEGES)) {
            throw new PrivilegeBadActionException("Action impossible sur ce privilège.");
        }

        Privilege exist = optional.get();

        exist.setShow(privilege.getShow());
        exist.setDescription(privilege.getDescription());
    }

    @Override
    @Transactional
    public void enable(boolean active, Long id) {
        Optional<Privilege> exist = repository.findById(id);

        if (!exist.isPresent()) {
            throw new PrivilegeNotFoundException("Le privilège n'existe pas");
        }

        Privilege privilege = exist.get();

        if (privilege.getName().equals(PrivilegeName.ALL_PRIVILEGES)) {
            throw new PrivilegeBadActionException("Action impossible sur ce privilège.");
        }

        privilege.setActive(active);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revisions<Integer, Privilege>> findAllHistory() {
        List<Revisions<Integer, Privilege>> result = new ArrayList<>();

        List<Privilege> privileges = repository.findAll();

        privileges.forEach(privilege -> {
            Revisions<Integer, Privilege> revisions = repository.findRevisions(privilege.getId());

            result.add(revisions);
        });

        return result;
    }
}
