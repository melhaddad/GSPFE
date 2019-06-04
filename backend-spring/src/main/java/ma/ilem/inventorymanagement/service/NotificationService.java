package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Notification;

import javax.mail.MessagingException;
import java.util.List;

public interface NotificationService {
    Notification save(Notification notification) throws MessagingException;

    void deleteById(Long id);

    List<Notification> findAll();

    List<Notification> findAllByUserEmail(String email);
}
