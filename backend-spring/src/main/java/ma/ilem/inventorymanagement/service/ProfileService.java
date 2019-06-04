package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileService {

    User details(String email);

    void changeImage(String email, String path, MultipartFile part) throws IOException;

    void changePassword(User user, String email);
}
