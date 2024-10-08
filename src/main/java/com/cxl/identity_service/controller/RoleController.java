package com.cxl.identity_service.controller;


import com.cxl.identity_service.dto.request.APIResponse;
import com.cxl.identity_service.dto.request.PermissionRequest;
import com.cxl.identity_service.dto.request.RoleRequest;
import com.cxl.identity_service.dto.response.PermissionResponse;
import com.cxl.identity_service.dto.response.RoleResponse;
import com.cxl.identity_service.service.PermissionService;
import com.cxl.identity_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;
    @PostMapping
    APIResponse<RoleResponse> create(@RequestBody RoleRequest request){
    return APIResponse.<RoleResponse>builder()
            .result(roleService.create(request))
            .build();
    }

    @GetMapping
    APIResponse<List<RoleResponse>> getAll(){
        return APIResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }
    @DeleteMapping("/{role}")
    APIResponse<Void> delete(@PathVariable String role){
    roleService.delete(role);
        return APIResponse.<Void>builder().build();

    }





}
