package com.example.airBndApp.Security;

import com.example.airBndApp.Dto.LoginDto;
import com.example.airBndApp.Dto.Request.SignUpRequestDto;
import com.example.airBndApp.Dto.Request.UserSDto;
import com.example.airBndApp.Dto.UserDto;
import com.example.airBndApp.Entity.Enum.Role;
import com.example.airBndApp.Entity.UserEntity;
import com.example.airBndApp.Repository.UserRepository;
import com.example.airBndApp.exception.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public UserSDto signUp(SignUpRequestDto signUpRequestDto) {
        UserEntity user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if (user != null){
            throw new RuntimeException("this email is already registered");
        }
        UserEntity newUser = modelMapper.map(signUpRequestDto, UserEntity.class);
        newUser.setRoles(Set.of(Role.USER));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser,UserSDto.class);
    }
    public String[] login(LoginDto loginDto){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.getEmail(),
            loginDto.getPassword()
            ));

    UserEntity user = (UserEntity) authentication.getPrincipal();
    String[] arr = new String[2];
    arr[0]=jwtService.generateAccessToken(user);

    arr[1]=jwtService.generateRefreshToken(user);

    return arr;
    }

    public String refreshToken(String refreshToken){
        Long id = jwtService.getUserIdByToken(refreshToken);

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("cookis not found by this id :"+ id));

        return jwtService.generateAccessToken(userEntity);
    }
}
