package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends RevisionRepository<Item, Long, Integer>, JpaRepository<Item, Long> {
    List<Item> findAllByDeletedIsFalse();

    Optional<Item> findByIdAndDeletedIsFalse(Long aLong);
}
