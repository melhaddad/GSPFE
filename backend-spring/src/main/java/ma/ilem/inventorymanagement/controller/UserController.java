package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Role;
import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SHOW_USERS', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAllByDeletedIsFalse();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SHOW_USERS', 'ALL_PRIVILEGES')")
    public ResponseEntity<User> findById(
            @PathVariable Long id
    ) {
        User user = userService.findById(id);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADD_USER', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> save(
            @Valid @RequestBody User user
    ) {
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER', 'ALL_PRIVILEGES')")
    public ResponseEntity update(
            @Valid @RequestBody User user,
            @PathVariable Long id
    ) {
        userService.update(user, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_USER', 'ALL_PRIVILEGES')")
    public ResponseEntity deleteById(
            @PathVariable Long id
    ) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/roles")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER_ROLE', 'ALL_PRIVILEGES')")
    public ResponseEntity roles(
            @Valid @RequestBody List<Role> roles,
            @PathVariable Long id
    ) {
        userService.roles(roles, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("history")
    @PreAuthorize("hasAnyAuthority('SHOW_HISTORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Revisions<Integer, User>>> history() {
        List<Revisions<Integer, User>> result = userService.findAllHistory();

        return ResponseEntity.ok(result);
    }
}
