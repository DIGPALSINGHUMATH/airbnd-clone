package com.example.airBndApp.Service;

import com.example.airBndApp.Dto.UserDto;
import com.example.airBndApp.Entity.UserEntity;

public interface UserService {

    UserDto getUserById(Long id);
}
