package com.cxl.identity_service.respository;
import com.cxl.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User,String> {
    Optional<User> findByUserName(String userName);
    boolean existsByuserName(String username);
}
