package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Role;
import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.exception.UserDuplicateEmailException;
import ma.ilem.inventorymanagement.exception.UserHasAssignmentException;
import ma.ilem.inventorymanagement.exception.UserNotFoundException;
import ma.ilem.inventorymanagement.repository.UserRepository;
import ma.ilem.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revisions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User save(User user) {
        User exist = userRepository.findByEmailAndDeletedIsFalse(user.getEmail());

        if (exist != null) {
            throw new UserDuplicateEmailException("L'adresse e-mail existe déjà.");
        }

        String passwordHash = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordHash);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (!optional.isPresent()) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        return optional.get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (!optional.isPresent()) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        User exist = optional.get();

        if (exist.getAssignment().size() > 0) {
            throw new UserHasAssignmentException("Impossible de supprimer un employé qui a des affectations.");
        }

        exist.setDeleted(true);
    }

    @Override
    @Transactional
    public void update(User user, Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (!optional.isPresent()) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        User exist = optional.get();

        exist.setFirstName(user.getFirstName());
        exist.setLastName(user.getLastName());
        exist.setAddress(user.getAddress());
        exist.setEmail(user.getEmail());
        exist.setFunction(user.getFunction());
    }

    @Override
    @Transactional
    public void roles(List<Role> roles, Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (!optional.isPresent()) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        User exist = optional.get();

        exist.getRoles().clear();

        exist.getRoles().addAll(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        User user = findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("L'employé n'existe pas.");
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmailAndDeletedIsFalse(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByDeletedIsFalse() {
        return userRepository.findAllByDeletedIsFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revisions<Integer, User>> findAllHistory() {
        List<Revisions<Integer, User>> resutl = new ArrayList<>();

        List<User> users = userRepository.findAll();

        users.forEach(user -> {
            Revisions<Integer, User> revisions = userRepository.findRevisions(user.getId());

            resutl.add(revisions);
        });

        return resutl;
    }
}
