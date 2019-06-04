package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Assignment;
import ma.ilem.inventorymanagement.entity.Item;
import ma.ilem.inventorymanagement.entity.Notification;
import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.exception.AssignmentNotFoundException;
import ma.ilem.inventorymanagement.exception.ItemInvalidQuantityException;
import ma.ilem.inventorymanagement.exception.ItemNotFoundException;
import ma.ilem.inventorymanagement.exception.UserNotFoundException;
import ma.ilem.inventorymanagement.pojo.AssignmentType;
import ma.ilem.inventorymanagement.pojo.NotificationType;
import ma.ilem.inventorymanagement.repository.AssignmentRepository;
import ma.ilem.inventorymanagement.repository.ItemRepository;
import ma.ilem.inventorymanagement.repository.UserRepository;
import ma.ilem.inventorymanagement.service.AssignmentService;
import ma.ilem.inventorymanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> findAll(String email) {
        User user = userRepository.findByEmailAndDeletedIsFalse(email);

        if (user == null) throw new UserNotFoundException("L'employé n'existe pas.");

        return repository.findAllByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> findAll(AssignmentType type) {
        return repository.findAllByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Assignment findById(Long id) {
        Optional<Assignment> userItem = repository.findById(id);

        if (!userItem.isPresent()) {
            throw new AssignmentNotFoundException("L'affectation n'existe pas.");
        }

        return userItem.get();
    }

    @Override
    @Transactional
    public Assignment request(Assignment assignment) {
        User user = userRepository.findByEmailAndDeletedIsFalse(assignment.getUser().getEmail());

        if (user == null) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

       // Optional<Item> itemOptional = itemRepository.findByIdAndDeletedIsFalse(assignment.getItem().getId());

        //if (!itemOptional.isPresent()) {
         //   throw new ItemNotFoundException("Le matériel n'existe pas.");
       // }

        assignment.setUser(user);
        assignment.setType(AssignmentType.REQUESTED);

        return repository.save(assignment);
    }

    @Override
    @Transactional
    public void accept(Long id, Long itemId, Long currentQuantity) {

        Optional<Assignment> optional = repository.findById(id);

        if (optional.isPresent()) {
            optional.get().setQuantity(currentQuantity);
            Item item = itemRepository.getOne(itemId);
            optional.get().setItem(item);

            Long usedQuantity = item.getUsedQuantity() + optional.get().getQuantity();
            Long quantity = item.getQuantity();
            double stock = (double) usedQuantity / (double) quantity;
            int percentage = 100 - (int) (stock * 100);

            if (stock > 1) {
                throw new ItemInvalidQuantityException("Quantité insuffisante.");
            } else if (stock >= (double) item.getInfo() / 100d) {
                List<User> users = userRepository.findAllCanReceiveStockNotifications();

                NotificationType type = NotificationType.INFO;
                String message = "Le stock du matériel <b class=\"primary--text\">" + item.getName() + "</b> est en diminution (Moins de " + percentage + "%).";

                if (stock == 1) {
                    type = (NotificationType.ERROR);
                    message = ("Le stock du matériel <b class=\"primary--text\">" + item.getName() + "</b> est totalement épuisé (" + percentage + "%).");
                } else if (stock >= (double) item.getError() / 100d) {
                    type = (NotificationType.ERROR);
                    message = ("Le stock du matériel <b class=\"primary--text\">" + item.getName() + "</b> est presque épuisé (Moins de " + percentage + "%).");
                } else if (stock >= (double) item.getWarning() / 100d) {
                    type = (NotificationType.WARNING);
                    message = ("Le stock du matériel <b class=\"primary--text\">" + item.getName() + "</b> est dans un état d'alert (Moins de " + percentage + "%).");
                }

                String finalMessage = message;
                NotificationType finalType = type;

                users.forEach(user -> {
                    Notification notification = new Notification();
                    notification.setDeleted(false);
                    notification.setUser(user);

                    notification.setType(finalType);
                    notification.setMessage(finalMessage);

                    try {
                        notificationService.save(notification);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        update(id, AssignmentType.ACCEPTED);
    }

    @Override
    public void refuse(Long id) {
        update(id, AssignmentType.REFUSED);
    }

    @Override
    public void deliver(Long id) {
        update(id, AssignmentType.DELIVERED);
    }

    @Override
    public void returning(Long id, String email) {
        update(id, AssignmentType.RETURNING, email);
    }

    @Override
    public void returned(Long id) {
        update(id, AssignmentType.RETURNED);
    }

    @Override
    @Transactional
    public void update(Long id, AssignmentType type) {
        Optional<Assignment> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new AssignmentNotFoundException("L'affectation n'existe pas.");
        }

        Assignment assignment = optional.get();

        assignment.setType(type);

        repository.save(assignment);
    }

    @Override
    @Transactional
    public void update(Long id, AssignmentType type, String email) {
        Optional<Assignment> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new AssignmentNotFoundException("L'affectation n'existe pas.");
        }

        User user = userRepository.findByEmailAndDeletedIsFalse(email);

        if (user == null) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        Assignment assignment = optional.get();

        if (!user.getId().equals(assignment.getUser().getId())) {
            throw new org.springframework.security.access.AccessDeniedException("");
        }

        assignment.setType(type);

        repository.save(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revisions<Integer, Assignment>> findAllHistory() {
        List<Revisions<Integer, Assignment>> result = new ArrayList<>();

        List<Assignment> assignments = repository.findAll();

        assignments.forEach(userItem -> {
            Revisions<Integer, Assignment> revisions = repository.findRevisions(userItem.getId());

            result.add(revisions);
        });

        return result;
    }
}
