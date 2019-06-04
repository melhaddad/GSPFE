package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends RevisionRepository<User, Long, Integer>, JpaRepository<User, Long> {
    User findByEmailAndDeletedIsFalse(String email);

    List<User> findAllByDeletedIsFalse();

    Optional<User> findByIdAndDeletedIsFalse(Long aLong);

    @Query("select u from User as u join u.roles as r join r.privileges as p where u.deleted = false and r.deleted = false and ((p.active = true and p.name = 'RECEIVE_STOCK_NOTIFICATIONS') or p.name = 'ALL_PRIVILEGES')")
    List<User> findAllCanReceiveStockNotifications();
}
