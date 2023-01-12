package com.kostserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kostserver.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query(value = "SELECT id, created_date, is_deleted, updated_date, address, birth_date, document_url, fullname, gender, job, phone_number, photo_url, account_id_id FROM user_profile where user_profile.id=?1", nativeQuery = true)
    public UserProfile getDetailUser(Long id);
}
