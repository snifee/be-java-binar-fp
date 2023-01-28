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
                "cloud_name", "dkmgqnqnw",
                "api_key", "814961217953268",
                "api_secret", "PNxu_Pcg4SM8QE30PmJ9eNIWEok"));
    }
}
