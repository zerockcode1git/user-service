package org.zerock.user_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.zerock.user_service.dto.RefreshTokenDTO;
import org.zerock.user_service.dto.UserDTO;
import org.zerock.user_service.redis.repository.RefreshTokenRepository;
import org.zerock.user_service.service.UserManageService;
import org.zerock.user_service.util.JWTProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@PostMapping("refresh")
public ResponseEntity<?> refresh(@RequestBody RefreshTokenDTO tokenDTO) {

    String rt = tokenDTO.getRt();

    // JWTProvider의 isExpired()로 RT 만료 여부 직접 검증
    if (jwtProvider.isExpired(rt)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Refresh Token이 만료되었습니다. 다시 로그인해주세요.");
    }

    // 만료되지 않은 RT에서 사용자 정보(Claims) 추출
    Claims claims = jwtProvider.getClaims(rt);
    String userId = claims.getSubject();
    String rolename = (String) claims.get("rolename");
    String username = claims.get("username", String.class);

    // Redis에서 저장된 RT 조회 (Optional)
    Optional<String> savedRTResult = refreshTokenRepository.find(userId);

    // Redis에서 만료되서 삭제되었거나 문제가 있어서 제거된 경우 
    if (savedRTResult.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("저장된 세션이 존재하지 않습니다.");
    }

    String savedRT = savedRTResult.get();

    // 문제가 있는 상황 - 외부에서 갱신된 RT를 탈취한 공격자가 이전 RT로 요청을 보내는 경우
    if (!savedRT.equals(rt)) {
        // [보안 핵심] 이미 갱신되어 파기된 '이전 RT'로 요청이 들어옴 (탈취 상황)
        // 정당한 사용자와 공격자 모두의 세션을 강제 종료하여 피해 차단
        refreshTokenRepository.delete(userId);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("토큰 재사용(탈취 위험)이 감지되었습니다. 보안 조치로 세션이 종료됩니다.");
    }

    // 정상 RTR 처리: 새로운 AT & RT 생성
    UserDTO userDTO = new UserDTO();
    userDTO.setUserId(userId);
    userDTO.setRolename(rolename);
    userDTO.setUsername(username);

    String newAT = jwtProvider.generateToken(userDTO, 10);        // 10분
    String newRT = jwtProvider.generateToken(userDTO, 60 );   // 60분

    // Redis에 새로운 RT 저장 (기존 키 덮어쓰기)
    refreshTokenRepository.save(userId, newRT);

    // 클라이언트에 전달
    return ResponseEntity.ok(Map.of("accessToken", newAT, "refreshToken", newRT));
  }

}

