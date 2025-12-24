package com.example.airBndApp.Controller;

import com.example.airBndApp.Dto.LoginDto;
import com.example.airBndApp.Dto.Request.SignUpRequestDto;
import com.example.airBndApp.Dto.Request.UserSDto;
import com.example.airBndApp.Dto.Request.loginResponseDto;
import com.example.airBndApp.Security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserSDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return  new ResponseEntity<>(authService.signUp(signUpRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<loginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request , HttpServletResponse response){
        String[] token =authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", token[1]);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new loginResponseDto(token[0]));


    }

    @PostMapping("/refresh")
    public ResponseEntity<loginResponseDto> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found in cookies"));

        System.out.println("Raw Cookie header: " + request.getHeader("Cookie"));
        System.out.println("Cookies object: " + Arrays.toString(request.getCookies()));


        String accessToken = authService.   refreshToken(refreshToken);
        return ResponseEntity.ok(new loginResponseDto(accessToken));
    }

}
