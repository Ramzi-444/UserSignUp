package com.example.contacts_applitcation.service.impl;

import com.example.contacts_applitcation.io.UserRepository;
import com.example.contacts_applitcation.io.entity.UserEntity;
import com.example.contacts_applitcation.service.UserService;
import com.example.contacts_applitcation.shared.Utils;
import com.example.contacts_applitcation.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity storedUserDetails = userRepository.findByEmail(userDto.getEmail());
        if (storedUserDetails != null) {
            throw new RuntimeException("Record already exist");
        }


        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(utils.generateUserId(30));

        UserEntity storedUser = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser,returnValue);
        return returnValue;
    }

}
