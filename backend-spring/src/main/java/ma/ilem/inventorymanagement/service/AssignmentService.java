package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Assignment;
import ma.ilem.inventorymanagement.pojo.AssignmentType;
import org.springframework.data.history.Revisions;

import java.util.List;

public interface AssignmentService {
    List<Assignment> findAll();

    List<Assignment> findAll(String email);

    List<Assignment> findAll(AssignmentType type);

    Assignment findById(Long id);

    Assignment request(Assignment assignment);

    void accept(Long id, Long item, Long quantity);

    void refuse(Long id);

    void deliver(Long id);

    void returning(Long id, String email);

    void returned(Long id);

    void update(Long id, AssignmentType type);

    void update(Long id, AssignmentType type, String email);

    List<Revisions<Integer, Assignment>> findAllHistory();
}
