package ma.ilem.inventorymanagement.repository;

import ma.ilem.inventorymanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByDeletedIsFalseAndUserEmailOrderByDateDesc(String email);

    Optional<Notification> findByIdAndDeletedIsFalse(Long id);
}
