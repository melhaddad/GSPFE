package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.exception.UserNotFoundException;
import ma.ilem.inventorymanagement.repository.UserRepository;
import ma.ilem.inventorymanagement.service.ProfileService;
import ma.ilem.inventorymanagement.util.ImageUtil;
import ma.ilem.inventorymanagement.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User details(String email) {
        User user = userRepository.findByEmailAndDeletedIsFalse(email);

        if (user == null) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        return user;
    }

    @Override
    @Transactional
    public void changePassword(User user, String email) {
        User exist = userRepository.findByEmailAndDeletedIsFalse(email);

        if (exist == null) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        String passwordHash = passwordEncoder.encode(user.getPassword());

        exist.setPassword(passwordHash);
    }

    @Override
    @Transactional
    public void changeImage(String email, String path, MultipartFile part) throws IOException {
        User exist = userRepository.findByEmailAndDeletedIsFalse(email);

        if (exist == null) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }
        String image;

        image = UploadUtil.upload(part, path + "image", "jpg");

        String sourcePah = path + "image" + File.separator + image + ".jpg";
        String savePath = path + "image" + File.separator + image + "-360x180.jpg";

        ImageUtil.resize(sourcePah, savePath, "jpg", 360, 180);

        exist.setImage(image);
    }


}
