package com.kostserver.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserProfilePostDto {
    private Long id;
    private String fullname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String address;
    private int gender;
    private String job;
    private String phoneNumber;
    private String photoUrl;
    private String documentUrl;
    private MultipartFile imageFile;
    private MultipartFile docFile;
}
