package com.itmart.admin.user;

import com.itmart.itmartcommon.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
