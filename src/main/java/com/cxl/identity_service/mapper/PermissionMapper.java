package com.cxl.identity_service.mapper;

import com.cxl.identity_service.dto.request.PermissionRequest;
import com.cxl.identity_service.dto.response.PermissionResponse;
import com.cxl.identity_service.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
