package com.bookshop.repository;

import com.bookshop.model.Role;
import com.bookshop.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
