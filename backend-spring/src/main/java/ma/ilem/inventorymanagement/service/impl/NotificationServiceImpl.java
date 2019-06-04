package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Notification;
import ma.ilem.inventorymanagement.repository.NotificationRepository;
import ma.ilem.inventorymanagement.service.NotificationEmailService;
import ma.ilem.inventorymanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationEmailService notificationEmailService;

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findAllByUserEmail(String email) {
        return notificationRepository.findAllByDeletedIsFalseAndUserEmailOrderByDateDesc(email);
    }

    @Override
    @Transactional
    public Notification save(Notification notification) throws MessagingException {
        notificationEmailService.send(notification);
        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Notification> optional = notificationRepository.findByIdAndDeletedIsFalse(id);

        optional.ifPresent(notification -> notification.setDeleted(true));
    }
}
