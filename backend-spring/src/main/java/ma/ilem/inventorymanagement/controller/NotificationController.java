package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Notification;
import ma.ilem.inventorymanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Notification>> findAll(Principal principal) {
        String email = principal.getName();

        List<Notification> notifications = notificationService.findAllByUserEmail(email);

        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(
            @PathVariable Long id
    ) {
        notificationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
