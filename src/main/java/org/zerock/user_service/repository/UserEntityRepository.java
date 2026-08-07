package org.zerock.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.user_service.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String>{
  
}
