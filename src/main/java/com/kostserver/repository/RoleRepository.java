package com.kostserver.repository;

import com.kostserver.model.EnumRole;
import com.kostserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(EnumRole enumRole);

    Boolean existsByName(EnumRole enumRole);
}
