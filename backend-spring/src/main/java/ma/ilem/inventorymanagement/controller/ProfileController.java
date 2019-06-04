package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.security.Principal;

@RestController
@MultipartConfig
@RequestMapping("api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ServletContext context;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public User details(Principal user) {
        return profileService.details(user.getName());
    }

    @PostMapping("change_password")
    @PreAuthorize("hasAnyAuthority('UPDATE_PASSWORD', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> changePassword(Principal principal, @RequestBody User user) {
        profileService.changePassword(user, principal.getName());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("change_image")
    @PreAuthorize("hasAnyAuthority('UPDATE_PROFILE_IMAGE', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> changeImage(
            Principal principal,
            @RequestParam MultipartFile avatar
    ) throws IOException {
        String appPath = context.getRealPath("/");

        profileService.changeImage(principal.getName(), appPath, avatar);
        return ResponseEntity.noContent().build();
    }
}
