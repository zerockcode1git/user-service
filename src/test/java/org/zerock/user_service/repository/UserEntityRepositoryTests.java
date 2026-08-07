package org.zerock.user_service.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.zerock.user_service.config.SecurityConfig;
import org.zerock.user_service.entity.UserEntity;


@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(SecurityConfig.class)
class UserEntityRepositoryTests {

 @Autowired
 private UserEntityRepository userEntityRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;

 private UserEntity newUser(String userId) {
   return UserEntity.builder()
       .userId(userId)
       .userPw(passwordEncoder.encode("password123"))
       .username("Tester")
       .rolename("USER")
       .build();
 }

 @Test
 @Commit
 void saveAndFindById() {
   String userId = "test-" + UUID.randomUUID();

   UserEntity saved = userEntityRepository.save(newUser(userId));


   assertEquals(saved.getUserId(), userId);
 }
}
