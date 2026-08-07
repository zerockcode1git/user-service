package org.zerock.user_service.redis.repository;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {


 private final RedisTemplate<String, String> redisTemplate;
 private static final long REFRESH_TOKEN_TTL = 60L;
 private static final String PREFIX = "refresh:";


 // 저장 (로그인, 토큰 재발급 시)
 public void save(String userId, String refreshToken) {
   redisTemplate.opsForValue()
       .set(PREFIX + userId, refreshToken, Duration.ofMinutes(REFRESH_TOKEN_TTL));
 }


 // 조회 (재발급 요청 시 검증)
 public Optional<String> find(String userId) {
   return Optional.ofNullable(redisTemplate.opsForValue().get(PREFIX + userId));
 }


 // 삭제 (로그아웃 시)
 public void delete(String userId) {
   redisTemplate.delete(PREFIX + userId);
 }
}
