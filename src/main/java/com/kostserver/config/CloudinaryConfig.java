package com.kostserver.config;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "kosthub",
            "api_key", "618124265592915",
            "api_secret", "tjgZHec24_bcZZFCeVSd0ysL2qs"));
}
