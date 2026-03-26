package com.jugger.springcommerce.modules.user.repository;

import com.jugger.springcommerce.modules.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role,Long>{
    Optional<Role> findByName(String name);
}
