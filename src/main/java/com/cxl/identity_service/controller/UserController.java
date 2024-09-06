package com.cxl.identity_service.controller;

import com.cxl.identity_service.dto.request.APIResponse;
import com.cxl.identity_service.dto.request.UserCreationRequest;
import com.cxl.identity_service.dto.request.UserUpdateRequest;
import com.cxl.identity_service.dto.response.UserResponse;
import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    APIResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        APIResponse<User> apiResponse=new APIResponse<>();
        apiResponse.setResult(userService.createUser(request));
     return apiResponse;
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userID}")
    UserResponse getUsers(@PathVariable("userID") String userID){
    return userService.getUser(userID);
    }

    @PutMapping({"/{userID}"})
    UserResponse updateUser(@PathVariable("userID") String userID, @RequestBody UserUpdateRequest request){
        return  userService.updareUser(userID,request);
    }
    @DeleteMapping({"/{userID}"})
    String deleteUser(@PathVariable("userID") String userID){
        userService.del(userID);
        return "User had deleted";
    }
}
