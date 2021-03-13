package com.itmart.admin.user;

import com.itmart.itmartcommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

    Long countById(Long id);

    @Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
    @Modifying
    void updateEnabledStatus(Long id, boolean enabled);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %:keyword%")
    Page<User> findAll(String keyword, Pageable pageable);
}
