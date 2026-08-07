package org.zerock.user_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.user_service.dto.UserDTO;
import org.zerock.user_service.entity.UserEntity;
import org.zerock.user_service.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserManageService {


 private final UserEntityRepository userEntityRepository;
 private final PasswordEncoder passwordEncoder;

public String register(UserDTO dto) {

   UserEntity entity = UserEntity.builder()
   .userId(dto.getUserId())
   .userPw(passwordEncoder.encode(dto.getUserPw()))
   .username(dto.getUsername())
   .rolename(dto.getRolename())
   .build();

   UserEntity saved = userEntityRepository.save(entity);
   log.info("registered user: {}", saved.getUserId());
  
   return saved.getUserId();
 }

@Transactional(readOnly = true)
 public UserDTO login(String userId, String userpw) {
   UserEntity entity = userEntityRepository.findById(userId)
     //.orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
     .orElseThrow(() -> new org.zerock.user_service.exception.LoginException("User Not Found "));
   if (!passwordEncoder.matches(userpw, entity.getUserPw())) {
     //throw new IllegalArgumentException("Invalid password");
     throw new org.zerock.user_service.exception.LoginException("User Not Found " );
   }
   log.info("login success: {}", userId);
   return toDTO(entity);

 }


 @Transactional(readOnly = true)
 public UserDTO read(String userId) {
   UserEntity entity = userEntityRepository.findById(userId)
     .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
   return toDTO(entity);
 }

 private UserDTO toDTO(UserEntity entity) {
   return UserDTO.builder()
     .userId(entity.getUserId())
     .username(entity.getUsername())
     .rolename(entity.getRolename())
     .regDate(entity.getRegDate())
     .updateDate(entity.getUpdateDate())
     .build();
 }
}