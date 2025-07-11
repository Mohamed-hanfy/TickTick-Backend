package com.ticktick.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", "di9urelap",
                "api_key", "878389622362573",
                "api_secret", "0GJ5804dWJJUWKUsEZjSi2uxvYo"));
    }

}
