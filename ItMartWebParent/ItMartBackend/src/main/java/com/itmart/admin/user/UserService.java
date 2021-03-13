package com.itmart.admin.user;

import com.itmart.itmartcommon.entity.Role;
import com.itmart.itmartcommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    public static final int USERS_PER_PAGE = 5;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        if (keyword != null) {
            return userRepository.findAll(keyword, pageable);
        }

        return userRepository.findAll(pageable);
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = user.getId() != null;

        if (isUpdatingUser) {
            User userInDb = userRepository.getOne(user.getId());

            if (user.getPassword().isEmpty()) {
                user.setPassword(userInDb.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

        return userRepository.save(user);
    }

    public boolean isEmailUnique(Long id, String email) {
        User userInDb = userRepository.getUserByEmail(email);

        if (userInDb == null) return true;
        boolean isNewUser = id == null;
        if (isNewUser) {
            return false;
        }

        return userInDb.getId().equals(id);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User get(Long id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with Id " + id);
        }
    }

    public void delete(Long id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with Id " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Long id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }
}
