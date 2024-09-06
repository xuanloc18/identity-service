package com.cxl.identity_service.service;


import com.cxl.identity_service.dto.request.UserCreationRequest;
import com.cxl.identity_service.dto.request.UserUpdateRequest;
import com.cxl.identity_service.dto.response.UserResponse;
import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.exception.AppException;
import com.cxl.identity_service.exception.ErrorCode;
import com.cxl.identity_service.mapper.UserMapper;
import com.cxl.identity_service.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private UserMapper userMapper;

    public User createUser(UserCreationRequest request){

    if(userRespository.existsByuserName(request.getUserName())){
        throw new AppException(ErrorCode.USER_EXIT);
    }
    User user=userMapper.toUser(request);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));


    return  userRespository.save(user) ;

    }
    public List<User> getUsers(){
        return  userRespository.findAll();
    }
    public UserResponse getUser(String id){
        return  userMapper.toUserResponse(userRespository.findById(id).orElseThrow(()->new RuntimeException("user not found")));
    }
    public UserResponse updareUser(String userID, UserUpdateRequest request){
        User user=userRespository.findById(userID).orElseThrow(()->new RuntimeException("user not found"));
        user=userMapper.updateUser(user,request);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        return userMapper.toUserResponse(userRespository.save(user));
    }
    public void del(String id){
      userRespository.deleteById(id);
    }
}
