package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Notification;

import javax.mail.MessagingException;

public interface NotificationEmailService {
    void send(Notification notification) throws MessagingException;
}
