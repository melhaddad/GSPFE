package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends RevisionRepository<Category, Long, Integer>, JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findAllByDeletedIsFalse();

    Optional<Category> findByIdAndDeletedIsFalse(Long aLong);
}
