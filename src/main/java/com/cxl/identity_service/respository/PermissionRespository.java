package com.cxl.identity_service.respository;
import com.cxl.identity_service.entity.Permission;
import com.cxl.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRespository extends JpaRepository<Permission,String> {
}
