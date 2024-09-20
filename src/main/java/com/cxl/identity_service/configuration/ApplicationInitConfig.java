package com.cxl.identity_service.configuration;

import com.cxl.identity_service.dto.response.UserResponse;
import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.enums.Role;
import com.cxl.identity_service.respository.UserRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner (UserRespository userRespository){
       return  args -> {//args đại diện cho các câu lệnh
           if(userRespository.findByUserName("admin").isEmpty()){
              Set<String> roles=new HashSet<>();
              roles.add(Role.ADMIN.name());
               User user= User.builder()
                       .userName("admin")
                       .passWord(passwordEncoder.encode("admin"))
//                       .roles("ADMIN")
                        .build();
               userRespository.save(user);
               log.warn("admin user has been reated with default password:admin,please change it");
           }
       };
    }

}
