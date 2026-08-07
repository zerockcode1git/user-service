package org.zerock.user_service.controller.advice;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.user_service.exception.LoginException;

import lombok.extern.log4j.Log4j2;


@RestControllerAdvice
@Log4j2
public class UserControllerAdvice {


 @ExceptionHandler(LoginException.class)
 @ResponseStatus(HttpStatus.UNAUTHORIZED)
 public Map<String, String> handleLoginException(LoginException e) {


   log.error("login failed: ", e.getMessage());
     return Map.of("error", "Login failed: check your credentials");
 }
}
