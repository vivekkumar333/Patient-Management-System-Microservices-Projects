package com.vkt.pms.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistException(EmailAlreadyExistException ex, HttpServletRequest request){
        ExceptionResponse exResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Bad request")
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResponse);
    }

    @ExceptionHandler(InvalidUserPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPasswordException(InvalidUserPasswordException ex, HttpServletRequest request){
        ExceptionResponse exResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Password")
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request){
        ExceptionResponse exResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Username")
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResponse);
    }


// To handel jakarta.validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        List<String> errors = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(error -> error.getField()+" :"+error.getDefaultMessage())
                                .toList();

        ExceptionResponse exResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Validation error")
                .message(String.join("; ",errors))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, HttpServletRequest request){
        ExceptionResponse exResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal server error")
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionResponse> handleJwtException(JwtException ex, HttpServletRequest request){
        ExceptionResponse exResponse = ExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Invalid token")
                .message(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResponse);
    }


}
