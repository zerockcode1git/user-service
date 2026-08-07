package org.zerock.user_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.zerock.user_service.dto.UserDTO;
import org.zerock.user_service.redis.repository.RefreshTokenRepository;
import org.zerock.user_service.service.UserManageService;
import org.zerock.user_service.util.JWTProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {

  private final UserManageService manageService;
  private final JWTProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;



  @Value("${app.title}")
  private String title;

  @GetMapping("hello")
  public String[] hello() {

    log.info("title: " + title);

     return new String[]{"Hello", "User",  "Service", "Updated"};
  }

  @PostMapping("login")
 public Map<String,String> login(@RequestBody UserDTO dto) {

   UserDTO userDTO = manageService.login(dto.getUserId(), dto.getUserPw());

   //AT 10min
   String accessToken = jwtProvider.generateToken(userDTO, 10);
   //RT 60min
   String refreshToken = jwtProvider.generateToken(userDTO, 60);

   refreshTokenRepository.save(userDTO.getUserId(), refreshToken);

   return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
 }

}

