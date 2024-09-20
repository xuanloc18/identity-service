package com.cxl.identity_service.mapper;

import com.cxl.identity_service.dto.request.PermissionRequest;
import com.cxl.identity_service.dto.request.RoleRequest;
import com.cxl.identity_service.dto.response.PermissionResponse;
import com.cxl.identity_service.dto.response.RoleResponse;
import com.cxl.identity_service.entity.Permission;
import com.cxl.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target ="permissions",ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse (Role role);
}
