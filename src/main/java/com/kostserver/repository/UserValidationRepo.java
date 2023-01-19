package com.kostserver.repository.test;

import com.kostserver.model.entity.UserValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserValidationRepo extends JpaRepository<UserValidation, Long> {
}
