package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Role;
import ma.ilem.inventorymanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revisions;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Page<User> findAllPageable(Pageable pageable);

    User save(User user);

    User findById(Long id);

    void deleteById(Long id);

    void update(User user, Long id);

    List<User> findAll();

    User findByEmail(String email);

    void roles(List<Role> roles, Long id);

    List<User> findAllByDeletedIsFalse();

    List<Revisions<Integer, User>> findAllHistory();
}
