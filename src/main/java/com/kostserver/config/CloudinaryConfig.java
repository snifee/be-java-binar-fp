package com.kostserver.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    Cloudinary cloudinaryBean(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlw58mkfs",
                "api_key", "185435282714983",
                "api_secret", "9WNJ-pmFPsw2nFsqKMnWpIcMe7g"));
    }
}
