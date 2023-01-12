package com.kostserver.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostserver.model.UserProfile;
import com.kostserver.repository.UserProfileRepository;
import com.kostserver.service.UserService;

@Service
public class UserProfileServiceImpl {
    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }

    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public UserProfile getDetailUser(Long id) {
        return userProfileRepository.getDetailUser(id);
    }
}
