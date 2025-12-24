package com.example.airBndApp.Adivce;

import com.example.airBndApp.exception.ResourcesNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ApiResponce<?>> HandleNoResourceFound(ResourcesNotFoundException exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
//        return new ResponseEntity<>("Resource not Found" ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponce<?>> handleAuthenticationException(AuthenticationException exception){
        ApiError apiError= ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .build();
        return  buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(JwtException.class)
    public  ResponseEntity<ApiResponce<?>> handleJwtException(JwtException exception){
        ApiError apiError= ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .build();
        return  buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<ApiResponce<?>> handleAccessDeniedException(JwtException exception){
        ApiError apiError= ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .message(exception.getMessage())
                .build();
        return  buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponce<?>> errorHandle(Exception exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponce<?>> HandleValidSource(MethodArgumentNotValidException exception){
    List<String> errors =     exception.getBindingResult().getAllErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validationn are invalid ")
                .subError(errors)
                .build();
                return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponce<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponce<>(apiError),apiError.getStatus());
    }
}
