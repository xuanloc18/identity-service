package com.cxl.identity_service.respository;

import com.cxl.identity_service.entity.InvalidateToken;
import com.cxl.identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken,String> {
}
