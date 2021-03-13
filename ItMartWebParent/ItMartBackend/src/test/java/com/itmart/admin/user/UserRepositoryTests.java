package com.itmart.admin.user;

import com.itmart.itmartcommon.entity.Role;
import com.itmart.itmartcommon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreate_User_WithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user = new User("user@mail.com", "P4ssword", "John", "Doe");
        user.addRole(roleAdmin);

        User savedUser = repository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreate_User_WithTwoRoles() {
        User user = new User("user2@mail.com", "P4ssword", "Jane", "Doe");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        user.addRole(roleEditor);
        user.addRole(roleAssistant);

        User savedUser = repository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreate_100_Users() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            User user = new User("testuser" + i + "@mail.com",
                    "P4ssword", "user" + i, "test" + i);
            user.setEnabled(i % 2 == 0);
            Role role = new Role(i % 5 + 1);
            user.addRole(role);
            repository.save(user);
        });
    }

    @Test
    public void testList_AllUser() {
        List<User> users = repository.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void testGet_User_ById() {
        User user = repository.findById(1L).get();
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdate_UserDetails() {
        User user = repository.findById(1L).get();
        user.setEnabled(true);
        repository.save(user);
    }

    @Test
    public void testUpdate_UserRoles() {
        User user = repository.findById(2L).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        user.getRoles().remove(roleEditor);
        user.addRole(roleSalesperson);

        repository.save(user);
    }

    @Test
    public void testDelete_User() {
        Long userId = 2L;
        repository.deleteById(userId);
    }

    @Test
    public void testGet_User_ByEmail() {
        String email = "user@mail.com";
        User user = repository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCount_ById() {
        Long id = 1L;
        Long countById = repository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisable_User() {
        Long id = 1L;
        repository.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnable_User() {
        Long id = 1L;
        repository.updateEnabledStatus(id, true);
    }

    @Test
    public void testList_FirstPage() {
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repository.findAll(pageable);
        List<User> userList = page.getContent();

        userList.forEach(System.out::println);

        assertThat(userList.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "John";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repository.findAll(keyword, pageable);
        List<User> userList = page.getContent();

        userList.forEach(System.out::println);

        assertThat(userList.size()).isGreaterThan(0);
    }
}
