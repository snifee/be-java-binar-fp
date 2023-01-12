package com.kostserver.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.UserProfilePostDto;
import com.kostserver.model.UserProfile;

import com.kostserver.service.impl.UserProfileServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    UserProfileServiceImpl userProfileServiceImpl;

    @GetMapping
    public ResponseEntity getUserProfile(@RequestParam("id") Long id) {
        try {
            Map<String, Object> resp = new HashMap<>();
            if (userProfileServiceImpl.findById(id).isEmpty()) {
                resp.put("status", 404);
                resp.put("data", userProfileServiceImpl.findById(id));
                return new ResponseEntity(resp, HttpStatus.NOT_FOUND);
            }
            resp.put("status", 200);
            resp.put("data", userProfileServiceImpl.findById(id));
            return new ResponseEntity(resp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity postUserProfile(
            @ModelAttribute UserProfilePostDto userProfilePostDto) {
        try {
            // UserProfile userProfile =
            // userProfileServiceImpl.getDetailUser(userProfilePostDto.getId());
            UserProfile userProfile = new UserProfile();
            String contentType = userProfilePostDto.getImageFile().getContentType();
            if (!(contentType.equals("image/png")
                    || contentType.equals("image/jpg")
                    || contentType.equals("image/jpeg"))) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

            }
            if (userProfilePostDto.getImageFile().getSize() > 20000000) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dlw58mkfs",
                    "api_key", "185435282714983",
                    "api_secret", "9WNJ-pmFPsw2nFsqKMnWpIcMe7g"));

            Map<String, Object> resp = new HashMap<>();
            if (userProfilePostDto.getId() != null) {
                userProfile.setId(userProfilePostDto.getId());
            }
            if (userProfilePostDto.getFullname() != null) {
                userProfile.setFullname(userProfilePostDto.getFullname());
            }
            if (userProfilePostDto.getAddress() != null) {
                userProfile.setAddress(userProfilePostDto.getAddress());
            }
            if (userProfilePostDto.getJob() != null) {
                userProfile.setJob(userProfilePostDto.getJob());
            }
            if (userProfilePostDto.getPhoneNumber() != null) {
                userProfile.setPhoneNumber(userProfilePostDto.getPhoneNumber());
            }
            if (userProfilePostDto.getBirthDate() != null) {
                userProfile.setBirthDate(userProfilePostDto.getBirthDate());
            }
            if (userProfilePostDto.getGender() != userProfile.getGender()) {
                userProfile.setGender(userProfilePostDto.getGender());
            }
            if (!userProfilePostDto.getDocFile().isEmpty()) {
                Map doc = cloudinary.uploader().upload(userProfilePostDto.getDocFile().getBytes(),
                        ObjectUtils.emptyMap());
                userProfile.setDocumentUrl(String.valueOf(doc.get("url")));
            }
            if (!userProfilePostDto.getImageFile().isEmpty()) {
                Map img = cloudinary.uploader().upload(userProfilePostDto.getImageFile().getBytes(),
                        ObjectUtils.emptyMap());
                userProfile.setPhotoUrl(String.valueOf(img.get("url")));
            }
            Long millis = System.currentTimeMillis() + 3600000 * 7;
            java.sql.Date now = new java.sql.Date(millis);
            if (userProfile.getCreatedDate() == null) {
                userProfile.setCreatedDate(now);
            }
            userProfile.setUpdatedDate(now);
            userProfileServiceImpl.save(userProfile);
            resp.put("data", userProfile);
            resp.put("status", 200);
            return new ResponseEntity(resp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping
    // public ResponseEntity postUserProfile(@RequestBody UserProfile userProfile,
    // @ModelAttribute MultipartFile file) {
    // try {
    // String contentType = file.getContentType();
    // if (!(contentType.equals("image/png")
    // || contentType.equals("image/jpg")
    // || contentType.equals("image/jpeg"))) {
    // return new ResponseEntity(HttpStatus.BAD_REQUEST);

    // }
    // if (file.getSize() > 20000000) {
    // return new ResponseEntity(HttpStatus.BAD_REQUEST);
    // }
    // Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
    // "cloud_name", "dlw58mkfs",
    // "api_key", "185435282714983",
    // "api_secret", "9WNJ-pmFPsw2nFsqKMnWpIcMe7g"));
    // Map response = cloudinary.uploader().upload(file.getBytes(),
    // ObjectUtils.emptyMap());
    // Map<String, Object> resp = new HashMap<>();
    // resp.put("response", response.get("url"));
    // // userProfile.setPhotoUrl(response.get("url"));
    // // userProfileServiceImpl.save(userProfile);
    // resp.put("status", 200);
    // resp.put("data", responses);
    // return new ResponseEntity(resp, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity(HttpStatus.BAD_REQUEST);
    // }
    // }

    // @GetMapping
    // public List<UserProfile> getUser() {
    // return userProfileServiceImpl.findAll();
    // }
}
