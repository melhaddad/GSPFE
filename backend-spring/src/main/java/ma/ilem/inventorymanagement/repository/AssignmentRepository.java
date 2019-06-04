package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.entity.Assignment;
import ma.ilem.inventorymanagement.pojo.AssignmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends RevisionRepository<Assignment, Long, Integer>, JpaRepository<Assignment, Long> {

    List<Assignment> findAllByUser(User user);

    List<Assignment> findAllByType(AssignmentType type);
}
