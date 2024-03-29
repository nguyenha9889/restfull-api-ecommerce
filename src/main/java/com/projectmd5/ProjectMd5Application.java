package com.projectmd5;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjectMd5Application {

   public static void main(String[] args) {
      SpringApplication.run(ProjectMd5Application.class, args);
   }

   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   };
   @Bean
   public ModelMapper modelMapper(){
      return new ModelMapper();
   }

   @Bean
   public WebMvcConfigurer corsMappingConfigurer() {
      return new WebMvcConfigurer() {
         @Override
         public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api.myservice.com/**")
                  .allowedMethods("GET", "POST", "PUT", "DELETE")
                  .allowedOrigins("*")
                  .allowedHeaders("*")
                  .maxAge(3600);
         }
      };
   }
}
